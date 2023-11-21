package com.ssafy.diary.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.LoginActivity.Companion.OPEN_FRAGMENT
import com.ssafy.diary.databinding.FragmentJoinBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.util.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class JoinFragment : Fragment() {
    private val binding by lazy { FragmentJoinBinding.inflate(layoutInflater) }
    private val lActivity by lazy { activity as LoginActivity }
    private var isUsed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // 좌상단 뒤로 가기 아이콘
        binding.btnBack.setOnClickListener {
            lActivity.goBack(this)
        }

        // 회원가입 구현
        binding.btnJoin.setOnClickListener {
            val name = binding.editName.text.toString()
            val id = binding.editId.text.toString()
            val pass = binding.editPassword.text.toString()
            val email = binding.editEmail.text.toString()

            if(name.isNotEmpty() && id.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty()){
                if(!isUsed) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val user = User()
                        user.userNickname = name
                        user.userId = id
                        user.userPassword = pass
                        user.userEmail = email
                        if (RetrofitUtil.userService.join(user).body()!!) {
                            Toast.makeText(requireContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT)
                                .show()
                            lActivity.moveFragment(OPEN_FRAGMENT)
                        }
                    }
                } else{
                    Toast.makeText(requireContext(), "아이디 중복체크를 해주세요", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(requireContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 아이디 중복 체크
        binding.btnCheck.setOnClickListener {
            val id = binding.editId.text.toString()
            if(id.isNotEmpty()){
                CoroutineScope(Dispatchers.Main).launch {
                    if(RetrofitUtil.userService.isUsed(id).body()!!){
                        Toast.makeText(requireContext(), "이미 사용중인 아이디입니다", Toast.LENGTH_SHORT).show()
                        isUsed = true
                    } else{
                        Toast.makeText(requireContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show()
                        isUsed = false
                    }
                }
            } else{
                Toast.makeText(requireContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}