package com.ssafy.diary.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.OPEN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.databinding.FragmentLoginBinding
import com.ssafy.diary.util.SharedPreferencesUtil

class DiaryMainFragment : Fragment() {
    private val binding by lazy { FragmentDiaryMainBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // **************** 수정 필요 ******************
        // 긴급 로그아웃 구현
        binding.imageView.setOnClickListener {
            SharedPreferencesUtil(requireContext()).deleteUser()
            mActivity.goBack(this)
            mActivity.moveFragment(OPEN_FRAGMENT)
            Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

}