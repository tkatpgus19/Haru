package com.ssafy.diary.diary

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_DETAILS_FRAGMENT
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.MAIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DiaryMainFragment : Fragment() {
    private val binding by lazy { FragmentDiaryMainBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }
    lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = arguments!!.getString("date")!!
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

        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

//        var date = "${year}-${month+1}-${day}"
        binding.tvDate.text = date
        binding.tvDate.setOnClickListener {
            val dpListener = DatePickerDialog.OnDateSetListener { view, y, m, d ->
                year = y
                month = m
                day = d
                binding.tvDate.text = "${y}-${m+1}-${d}"
                date = "${y}-${m+1}-${d}"
            }
            val datePickerDialog = DatePickerDialog(requireContext(), R.style.MySpinnerDatePickerStyle, dpListener, year, month, day)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis - 1000
            datePickerDialog.show()
        }
        binding.imgTodayDiary.clipToOutline = true

        binding.btnBack.setOnClickListener {
            dActivity.finish()
        }

        binding.imageButton.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, date)
        }
        binding.imgTodayDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, date)
        }

        return binding.root
    }

}