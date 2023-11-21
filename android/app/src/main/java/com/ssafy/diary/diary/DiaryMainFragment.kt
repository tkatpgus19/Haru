package com.ssafy.diary.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_DETAILS_FRAGMENT
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.MAIN_FRAGMENT
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import java.text.SimpleDateFormat
import java.util.Date

class DiaryMainFragment : Fragment() {
    private val binding by lazy { FragmentDiaryMainBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }

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
//        binding.btnBack.setOnClickListener {
//            SharedPreferencesUtil(requireContext()).deleteUser()
//            mActivity.goBack(this)
//            mActivity.moveFragment(OPEN_FRAGMENT)
//            Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
//        }

        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = simpleDateFormat.format(date)

        binding.tvDate.text = currentDate

        binding.btnBack.setOnClickListener {
            dActivity.finish()
        }

        binding.imageButton.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT)
        }
        binding.imgTodayDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT)
        }

        return binding.root
    }

}