package com.ssafy.diary.nav

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.MainActivity
import com.ssafy.diary.R
import com.ssafy.diary.SubActivity
import com.ssafy.diary.config.ApplicationClass
import com.ssafy.diary.databinding.FragmentMyPageBinding
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {
    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }
    private lateinit var userId: String
    private lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val userImg = SharedPreferencesUtil(requireContext()).getUser().userImg
        if(userImg != ""){
            Glide.with(binding.imgPersonal)
                .load("${ApplicationClass.IMGS_URL}${userImg}")
                .into(binding.imgPersonal)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userId = SharedPreferencesUtil(requireContext()).getUser().userId
        userName = SharedPreferencesUtil(requireContext()).getUser().userNickname
        binding.textName.text = "${userName}님"

        val userImg = SharedPreferencesUtil(requireContext()).getUser().userImg
        Log.d("해위", userImg)
        if(userImg != ""){
            Glide.with(binding.imgPersonal)
                .load("${ApplicationClass.IMGS_URL}${userImg}")
                .into(binding.imgPersonal)
        }

        binding.btnMyInfo.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java)
            intent.putExtra("type", "mypage")
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener{
            // 긴급 로그아웃 구현
            SharedPreferencesUtil(requireContext()).deleteUser()
            Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
            mActivity.finish()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.btnDelete.setOnClickListener {
            showDialog()
        }
        return binding.root
    }
    private fun showDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater.inflate(R.layout.dialog_match_password, null)
        val editText = inflater.findViewById<EditText>(R.id.edit_password_dialog)
        val ok = inflater.findViewById<Button>(R.id.btn_ok)


        builder.apply {
            setView(inflater)

            ok.setOnClickListener {

                lifecycleScope.launch {
                    if (editText.text.isNotEmpty()) {
                        val pass = editText.text.toString()
                        val isMatch = RetrofitUtil.userService.matchPassword(userId, pass).body()
                        if (isMatch != null) {
                            val result = RetrofitUtil.userService.deleteAccount(userId).body()
                            if (result!!) {
                                Toast.makeText(
                                    requireContext(),
                                    "계정 탈퇴가 완료되었습니다...",
                                    Toast.LENGTH_SHORT
                                ).show()
                                SharedPreferencesUtil(requireContext()).deleteUser()
                                mActivity.finish()
                                startActivity(Intent(requireContext(), LoginActivity::class.java))
                            }
                        } else {
                            Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        builder.create().show()
    }

}