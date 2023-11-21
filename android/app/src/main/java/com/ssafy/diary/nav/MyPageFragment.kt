package com.ssafy.diary.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.MYINFO_PAGE_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.databinding.FragmentMyPageBinding
import com.ssafy.diary.util.SharedPreferencesUtil

class MyPageFragment : Fragment() {
    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }
    private lateinit var userId: String
    private lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userId = SharedPreferencesUtil(requireContext()).getUser().userId
        userName = SharedPreferencesUtil(requireContext()).getUser().userNickname
        binding.textName.text = userName

        binding.btnMyInfo.setOnClickListener {
            mActivity.moveFragment(MYINFO_PAGE_FRAGMENT)
        }

        binding.btnLogout.setOnClickListener{

        }

        binding.btnDelete.setOnClickListener {

        }
        return binding.root
    }

}