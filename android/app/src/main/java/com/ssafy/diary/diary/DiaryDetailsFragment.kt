package com.ssafy.diary.diary

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_MAIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.config.ApplicationClass
import com.ssafy.diary.databinding.FragmentDiaryDetailsBinding
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.dto.Diary
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.http.Headers
import retrofit2.http.Multipart
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class DiaryDetailsFragment : Fragment() {
    private val binding by lazy { FragmentDiaryDetailsBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }
    lateinit var userInfo: User
    lateinit var userId: String
    lateinit var date: String
    private var modify = false
    private var filePath = ""

    lateinit var year: String
    lateinit var month: String
    lateinit var day: String

    private var todayFeeling = ""
    private var diaryImg: MultipartBody.Part? = MultipartBody.Part.createFormData("diaryImg", "asdfsafsadfasdfsa")
    private var prevImg = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = arguments!!.getString("year")!!
        month = arguments!!.getString("month")!!
        day = arguments!!.getString("day")!!
        date = "${year}-${month}-${day}"
        binding.tvDate.text = date
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val emotions =
            mapOf<TextView, String>(binding.feeling01 to "\uD83D\uDE0A", binding.feeling02 to "\uD83D\uDE06", binding.feeling03 to "\uD83D\uDE02", binding.feeling04 to "\uD83D\uDE25",
                binding.feeling05 to "\uD83D\uDE2D", binding.feeling06 to "\uD83D\uDE14", binding.feeling07 to "\uD83D\uDE24", binding.feeling08 to "\uD83D\uDE21",
                binding.feeling09 to "\uD83E\uDD2C", binding.feeling10 to "\uD83E\uDD12", )

        userInfo = SharedPreferencesUtil(requireContext()).getUser()
        userId = userInfo.userId
        binding.btnBack.setOnClickListener {
            dActivity.goBack(this)
        }

        binding.editTextTodayDiary.movementMethod = ScrollingMovementMethod.getInstance()
        binding.tvDate.text = date

        lifecycleScope.launch {
            val result = RetrofitUtil.diaryService.getDiary(userId, date).body()
            if(result!!.userId == null){
                binding.feeling.visibility = View.INVISIBLE
                binding.emotionContainer.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.INVISIBLE
                binding.btnEdit.visibility = View.INVISIBLE
                binding.editTextTodayDiary.isEnabled = true
                binding.btnUpload.visibility = View.VISIBLE
            } else{
                binding.feeling.visibility = View.VISIBLE
                binding.feeling.text = result.diaryEmotion
                binding.emotionContainer.visibility = View.GONE
                binding.editTextTodayDiary.setText(result.diaryContent)
                binding.btnSave.visibility = View.INVISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.VISIBLE
                todayFeeling = result.diaryEmotion
                binding.editTextTodayDiary.isEnabled = false
                if(result.diaryImg != null){
                    Glide.with(binding.btnImgChoose)
                        .load("${ApplicationClass.MENU_IMGS_URL}${result.diaryImg}")
                        .into(binding.btnImgChoose)
                    prevImg = result.diaryImg
                }
            }
        }

        binding.btnSave.setOnClickListener {
            if(binding.editTextTodayDiary.text.isNotEmpty()){
                lifecycleScope.launch {
                    val diary = Diary()
                    diary.diaryContent = binding.editTextTodayDiary.text.toString()
                    diary.userId = userId
                    diary.diaryDate = date
                    diary.diaryEmotion = todayFeeling
                    diary.diaryImg = prevImg
                    if(!modify) {
                        Log.d("해위", diaryImg.toString())
                        val result = RetrofitUtil.diaryService.saveDiary(diary, diaryImg!!).body()!!
                        if (result) {
                            Toast.makeText(requireContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show()

                            binding.feeling.visibility = View.VISIBLE
                            binding.emotionContainer.visibility = View.GONE
                            binding.btnSave.visibility = View.INVISIBLE
                            binding.btnDelete.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.VISIBLE
                            binding.editTextTodayDiary.isEnabled = false

                            RetrofitUtil.userService.updateHeart(userId, (userInfo.userHeart+1).toString())
                            SharedPreferencesUtil(requireContext()).updateHeart(userInfo.userHeart+1)
                        }
                    } else{
                        val result = RetrofitUtil.diaryService.updateDiary(diary, diaryImg!!).body()
                        if(result!!){
                            Toast.makeText(requireContext(), "수정 되었습니다", Toast.LENGTH_SHORT).show()

                            binding.feeling.visibility = View.VISIBLE
                            binding.emotionContainer.visibility = View.GONE
                            binding.btnSave.visibility = View.INVISIBLE
                            binding.btnDelete.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.VISIBLE
                            binding.editTextTodayDiary.isEnabled = false
                        }
                    }
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            lifecycleScope.launch {
                modify = true

                binding.btnUpload.visibility = View.VISIBLE
                binding.emotionContainer.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.INVISIBLE
                binding.btnEdit.visibility = View.INVISIBLE

                binding.editTextTodayDiary.isEnabled = true
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                val result = RetrofitUtil.diaryService.deleteDiary(userId, date).body()
                if(result!!){
                    Toast.makeText(requireContext(), "삭제 되었습니다", Toast.LENGTH_SHORT).show()
                    RetrofitUtil.userService.updateHeart(userId, (userInfo.userHeart-1).toString())
                    SharedPreferencesUtil(requireContext()).updateHeart(userInfo.userHeart-1)
                    dActivity.goBack(this@DiaryDetailsFragment)
                }
            }
        }

        emotions.forEach {
            val key = it.key
            it.key.setOnClickListener {
                todayFeeling = emotions[key]!!
                binding.feeling.text = todayFeeling
                binding.feeling.visibility = View.VISIBLE

            }
        }

        binding.btnUpload.setOnClickListener {
            selectGallery()
        }
        return binding.root
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            filePath = getFilePathUri(result.data?.data!!)

            Glide.with(binding.btnImgChoose)
                .load(filePath)
                .into(binding.btnImgChoose)
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
        diaryImg = MultipartBody.Part.createFormData("diaryImg", file.name, requestFile)
    }
    companion object{
        const val REQ_GALLERY = 1
    }
}