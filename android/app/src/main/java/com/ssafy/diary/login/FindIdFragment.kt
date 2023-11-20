package com.ssafy.diary.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.LoginActivity.Companion.LOGIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentFindIdBinding
import com.ssafy.diary.util.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindIdFragment : Fragment() {
    private val binding by lazy { FragmentFindIdBinding.inflate(layoutInflater) }
    private val lActivity by lazy { activity as LoginActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // 좌상단 뒤로 가기 아이콘 이벤트 구현
        binding.btnBack.setOnClickListener {
            lActivity.goBack(this)
        }

        // 로그인 프래그먼트로 이동
        binding.btnLogin.setOnClickListener {
            lActivity.moveFragment(LOGIN_FRAGMENT)
        }

        // 아이디 찾기
        binding.btnFindId.setOnClickListener {
            val email = binding.editEmail.text.toString()
            if(email.isNotEmpty()){
                CoroutineScope(Dispatchers.Main).launch {
                    val result = RetrofitUtil.userService.findId(email).body()
                    if(result != "null"){
                        showDialog("당신의 아이디는 ${result}입니다.")
                    } else{
                        showDialog("해당 이메일로 등록된 아이디가 없습니다.")
                    }
                }
            } else{
                Toast.makeText(requireContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showDialog(result: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater.inflate(R.layout.dialog_find_id, null)
        val textView = inflater.findViewById<TextView>(R.id.tv_dialog)
        builder.apply {
            setView(inflater)
            setPositiveButton("확인"){ dialog, _ ->
                dialog.cancel()
            }
        }
        textView.text = result
        builder.create().show()
    }

}