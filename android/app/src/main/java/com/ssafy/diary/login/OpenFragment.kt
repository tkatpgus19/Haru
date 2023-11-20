package com.ssafy.diary.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.LoginActivity.Companion.JOIN_FRAGMENT
import com.ssafy.diary.LoginActivity.Companion.LOGIN_FRAGMENT
import com.ssafy.diary.databinding.FragmentOpenBinding


class OpenFragment : Fragment() {
    private val binding by lazy { FragmentOpenBinding.inflate(layoutInflater) }
    private val lActivity by lazy { activity as LoginActivity }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        
        // 로그인 프래그먼트로 이동
        binding.btnLogin.setOnClickListener {
            lActivity.moveFragment(LOGIN_FRAGMENT)
        }

        // 회원가입 프래그먼트로 이동
        binding.btnJoin.setOnClickListener {
            lActivity.moveFragment(JOIN_FRAGMENT)
        }
        return binding.root
    }

}