package com.ssafy.diary.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.JOIN_FRAGMENT
import com.ssafy.diary.MainActivity.Companion.LOGIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.databinding.FragmentOpenBinding


class OpenFragment : Fragment() {
    private val binding by lazy { FragmentOpenBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }
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
            mActivity.moveFragment(LOGIN_FRAGMENT)
        }

        // 회원가입 프래그먼트로 이동
        binding.btnJoin.setOnClickListener {
            mActivity.moveFragment(JOIN_FRAGMENT)
        }
        return binding.root
    }

}