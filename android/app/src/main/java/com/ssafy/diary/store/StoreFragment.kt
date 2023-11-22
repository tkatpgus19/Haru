package com.ssafy.diary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentMyPageBinding
import com.ssafy.diary.databinding.FragmentStoreBinding
import com.ssafy.diary.util.SharedPreferencesUtil

class StoreFragment : Fragment() {
    private val binding by lazy { FragmentStoreBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val userInfo = SharedPreferencesUtil(requireContext()).getUser()
        binding.textHeartCount.text = "${userInfo.userHeart}개"
        return binding.root
    }

}