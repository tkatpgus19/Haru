package com.ssafy.diary.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.LOGIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentFindIdBinding
import com.ssafy.diary.databinding.FragmentOpenBinding
import com.ssafy.diary.util.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindIdFragment : Fragment() {
    private val binding by lazy { FragmentFindIdBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // 좌상단 뒤로 가기 아이콘 이벤트 구현
        binding.imageView.setOnClickListener {
            mActivity.goBack(this)
        }

        // 로그인 프래그먼트로 이동
        binding.btnLogin.setOnClickListener {
            mActivity.moveFragment(LOGIN_FRAGMENT)
        }

        // 아이디 찾기
        binding.btnFindId.setOnClickListener {
            val email = binding.editEmail.text.toString()
            if(email.isNotEmpty()){
                CoroutineScope(Dispatchers.Main).launch {
                    val result = RetrofitUtil.userService.findId(email).body()
                    if(result != null){
                        Toast.makeText(requireContext(), "아이디는 ${result}입니다", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(requireContext(), "해당 이메일로 가입한 이력이 없습니다...", Toast.LENGTH_SHORT).show()
                    }
                }
            } else{
                Toast.makeText(requireContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}