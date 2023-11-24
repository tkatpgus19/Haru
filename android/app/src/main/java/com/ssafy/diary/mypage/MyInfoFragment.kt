package com.ssafy.diary.mypage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.diary.R
import com.ssafy.diary.SubActivity
import com.ssafy.diary.config.ApplicationClass
import com.ssafy.diary.databinding.FragmentMyInfoBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MyInfoFragment : Fragment() {
    private val binding by lazy { FragmentMyInfoBinding.inflate(layoutInflater) }
    private val sActiity by lazy { activity as SubActivity }
    private var userInfo = User()
    private var filePath = ""
    private var userImg: MultipartBody.Part? = MultipartBody.Part.createFormData("diaryImg", "asdfsafsadfasdfsa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding.editId.isEnabled = false
        binding.editPassword.isEnabled = false
        userInfo = SharedPreferencesUtil(requireContext()).getUser()

        // 사용자 정보 로드
        binding.editEmail.setText(userInfo.userEmail)
        binding.editName.setText(userInfo.userNickname)
        binding.editId.setText(userInfo.userId)
        binding.btnChangePassword.setOnClickListener {
            showDialog()
        }
        
        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            sActiity.finish()
        }
        
        // 프로필 이미지 선택
        binding.imgPersonal.setOnClickListener {
            selectGallery()

        }
        Glide.with(binding.imgPersonal)
            .load("${ApplicationClass.USER_IMGS_URL}${userInfo.userImg}")
            .into(binding.imgPersonal)

        // 수정 정보 저장
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val user = RetrofitUtil.userService.getUserInfo(userInfo.userId).body()!!
                user.userHeart = userInfo.userHeart
                user.userEmail = binding.editEmail.text.toString()
                user.userId = userInfo.userId
                if(binding.editPassword.text.isNotEmpty()) {
                    user.userPassword = binding.editPassword.text.toString()
                }
                user.userNickname = binding.editName.text.toString()
                val result = RetrofitUtil.userService.update(user).body()
                SharedPreferencesUtil(requireContext()).addUser(user)
                if(result!!){
                    Toast.makeText(requireContext(), "수정했습니다.", Toast.LENGTH_SHORT).show()
                    sActiity.finish()
                }
            }
        }
        return binding.root
    }

    private fun showDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater.inflate(R.layout.dialog_match_password, null)
        val editText = inflater.findViewById<EditText>(R.id.edit_password_dialog)

        val ok = inflater.findViewById<Button>(R.id.btn_ok)

        builder.setView(inflater)
        val ad = builder.create()
        ad.show()

        ok.setOnClickListener {
            lifecycleScope.launch {
                if (editText.text.isNotEmpty()) {
                    val pass = editText.text.toString()
                    val result = RetrofitUtil.userService.matchPassword(userInfo.userId, pass).body()!!.userId
                    if(result == null){
                        Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    } else{
                        binding.editPassword.isEnabled = true;
                        binding.editPassword.setText(pass)
                        binding.textPasswordBlocked.visibility = View.GONE
                    }
                } else{
                    Toast.makeText(requireContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                }
            }
            ad.cancel()
        }

    }
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            filePath = getFilePathUri(result.data?.data!!)

            Glide.with(binding.imgPersonal)
                .load(filePath)
                .into(binding.imgPersonal)
            setImage()

        }
    }
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if(writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQ_GALLERY
            )
        }else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            imageResult.launch(intent)
        }
    }

    // 절대경로 변환
    fun getFilePathUri(path: Uri): String {
        val buildName = Build.MANUFACTURER

        // 샤오미 폰은 바로 경로 반환 가능
        if (buildName.equals("Xiaomi")) {
            return path.path.toString()
        }

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = requireActivity().contentResolver.query(path, proj, null, null, null)

        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(columnIndex)
    }

    fun setImage(){
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        userImg = MultipartBody.Part.createFormData("userImg", file.name, requestFile)
        lifecycleScope.launch{
            RetrofitUtil.userService.updateImage(userInfo.userId, userImg!!)
            val tmp = RetrofitUtil.userService.getImage(userInfo.userId).body()
            SharedPreferencesUtil(requireContext()).addUserImg(tmp!!)
            Toast.makeText(requireContext(), "프로필 이미지를 수정했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    companion object{
        const val REQ_GALLERY = 1
    }
}