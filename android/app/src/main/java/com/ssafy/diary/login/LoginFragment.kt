package com.ssafy.diary.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.DIARY_MAIN_FRAGMENT
import com.ssafy.diary.MainActivity.Companion.FIND_ID_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
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

        // 로그인 구현
        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString()
            val pass = binding.editPassword.text.toString()

            // editText 값이 비어있지 않을 때 로그인 요청
            if(id.isNotEmpty() && pass.isNotEmpty()){
                CoroutineScope(Dispatchers.Main).launch {
                    val user = User()
                    user.userId = id
                    user.userPassword = pass

                    // 서버로 로그인 요청
                    val result = RetrofitUtil.userService.login(user).body()

                    // 로그인에 성공하면 서버로 부터 받은 회원 정보 sharedPreference 에 저장
                    if(result!!.userId != null){
                        Toast.makeText(requireContext(), "반갑습니다 ${result.userNickname}님", Toast.LENGTH_SHORT).show()
                        SharedPreferencesUtil(requireContext()).addUser(user)
                        mActivity.moveFragment(DIARY_MAIN_FRAGMENT)
                    } else{
                        Toast.makeText(requireContext(), "아이디 혹은 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // editText 값이 비어있을 때
            else{
                Toast.makeText(requireContext(), "아이디와 비밀번호를 모두 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
        
        // 아이디 조회 프래그먼트 이동
        binding.btnFindId.setOnClickListener {
            mActivity.moveFragment(FIND_ID_FRAGMENT)
        }
        return binding.root
    }

}