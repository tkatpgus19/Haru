package com.ssafy.diary.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.MainActivity
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.databinding.FragmentMyInfoBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class MyInfoFragment : Fragment() {
    private val binding by lazy { FragmentMyInfoBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }
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
        binding.btnChangePassword.setOnClickListener {
            showDialog()
        }

        return binding.root
    }

    private fun showDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater.inflate(R.layout.dialog_match_password, null)
        val editText = inflater.findViewById<EditText>(R.id.et_match_dialog)
        builder.apply {
            setView(inflater)
            setPositiveButton("확인"){ dialog, _ ->
                lifecycleScope.launch {
                    if (editText.text.isNotEmpty()) {
                        val pass = editText.text.toString()
                        Log.d("해위", pass)
                        val isMatch = RetrofitUtil.userService.matchPassword(userInfo.userId, pass).body()
                        Log.d("해위", isMatch.toString())
                        if(isMatch != null){
                            val result = RetrofitUtil.userService.deleteAccount(userInfo.userId).body()
                            if(result!!){
                                binding.editPassword.isEnabled = true;
                                binding.textPasswordBlocked.visibility = View.GONE
                            }
                        } else{
                            Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(requireContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.cancel()
            }
        }
        builder.create().show()
    }
}