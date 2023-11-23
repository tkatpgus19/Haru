package com.ssafy.diary.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.MainActivity
import com.ssafy.diary.R
import com.ssafy.diary.SubActivity
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.databinding.FragmentMyInfoBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class MyInfoFragment : Fragment() {
    private val binding by lazy { FragmentMyInfoBinding.inflate(layoutInflater) }
    private val sActiity by lazy { activity as SubActivity }
    private var userInfo = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding.editId.isEnabled = false
        binding.editPassword.isEnabled = false

        userInfo = SharedPreferencesUtil(requireContext()).getUser()

        binding.editEmail.setText(userInfo.userEmail)
        binding.editName.setText(userInfo.userNickname)
        binding.editId.setText(userInfo.userId)
        binding.btnChangePassword.setOnClickListener {
            showDialog()
        }
        binding.btnBack.setOnClickListener {
            sActiity.finish()
        }

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val user = User()
                user.userHeart = userInfo.userHeart
                user.userEmail = binding.editEmail.text.toString()
                user.userId = userInfo.userId
                user.userPassword = binding.editPassword.text.toString()
                user.userNickname = binding.editName.text.toString()
                val result = RetrofitUtil.userService.update(user).body()
                if(result!!){
                    Toast.makeText(requireContext(), "수정했습니다.", Toast.LENGTH_SHORT).show()
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


        builder.apply {
            setView(inflater)

            ok.setOnClickListener {
//            ok.setTextColor(Color.parseColor("#FFFFFF"))
//            ok.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.lightBrown)) //(Color.parseColor("#72635D"))

                lifecycleScope.launch {
                    if (editText.text.isNotEmpty()) {
                        val pass = editText.text.toString()
                        if(RetrofitUtil.userService.matchPassword(userInfo.userId, pass).body() != null){
                            binding.editPassword.isEnabled = true;
                            binding.editPassword.setText(pass)
                            binding.textPasswordBlocked.visibility = View.GONE
                        } else{
                            Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(requireContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
        builder.create().show()
    }
//        builder.apply {
//            setView(inflater)
//            setPositiveButton("확인"){ dialog, _ ->
//                lifecycleScope.launch {
//                    if (editText.text.isNotEmpty()) {
//                        val pass = editText.text.toString()
//                        if(RetrofitUtil.userService.matchPassword(userInfo.userId, pass).body() != null){
//                            binding.editPassword.isEnabled = true;
//                            binding.editPassword.setText(pass)
//                            binding.textPasswordBlocked.visibility = View.GONE
//                        } else{
//                            Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
//                        }
//                    } else{
//                        Toast.makeText(requireContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                dialog.cancel()
//            }
//        }
//        builder.create().show()
}