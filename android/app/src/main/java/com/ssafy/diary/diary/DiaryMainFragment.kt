package com.ssafy.diary.diary

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_DETAILS_FRAGMENT
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.MAIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class DiaryMainFragment : Fragment() {
    private val binding by lazy { FragmentDiaryMainBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }
    private val question = arrayOf("질문 1", "질문 2", "질문 3", "질문 4", "질문 5","질문 6" )
    lateinit var userId: String

    lateinit var year: String
    lateinit var month: String
    lateinit var day: String
    lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = arguments!!.getString("year")!!
        month = arguments!!.getString("month")!!
        day = arguments!!.getString("day")!!
        date = "${year}-${month}-${day}"
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

//        var date = "${year}-${month+1}-${day}"

        userId = SharedPreferencesUtil(requireContext()).getUser().userId

        lifecycleScope.launch {
            val result = RetrofitUtil.homeworkService.getHomework(userId, date).body()
            if(result!!.userId != null){
                binding.textTodayQuestion.text = result.homeworkQuestion
                binding.btnSave.visibility = View.GONE
                binding.btnEdit.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
                binding.editTextTodayQuestionAnswer.isEnabled = false
            } else{
                binding.textTodayQuestion.text = question[(0..5).random()]
                binding.btnSave.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE
                binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                binding.editTextTodayQuestionAnswer.isEnabled = true
            }
        }


        binding.tvDate.text = date
        binding.tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dpListener = DatePickerDialog.OnDateSetListener { view, y, m, d ->
                year = y.toString()
                month = (m+1).toString()
                day = d.toString()
                date = "${y}-${m+1}-${"%02d".format(d)}"
                binding.tvDate.text = date
                lifecycleScope.launch {
                    val result = RetrofitUtil.homeworkService.getHomework(userId, date).body()
                    if(result!!.userId != null){
                        binding.textTodayQuestion.text = result.homeworkQuestion
                        binding.btnSave.visibility = View.GONE
                        binding.btnEdit.visibility = View.VISIBLE
                        binding.btnDelete.visibility = View.VISIBLE
                        binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
                        binding.editTextTodayQuestionAnswer.isEnabled = false
                    } else{
                        binding.textTodayQuestion.text = question[(0..5).random()]
                        binding.btnSave.visibility = View.VISIBLE
                        binding.btnEdit.visibility = View.GONE
                        binding.btnDelete.visibility = View.GONE
                        binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                        binding.editTextTodayQuestionAnswer.isEnabled = true
                    }
                }
            }
            Log.d("뭔데", year+month+day)
            val datePickerDialog = DatePickerDialog(requireContext(), R.style.MySpinnerDatePickerStyle, dpListener, year.toInt(), month.toInt(), day.toInt())
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis - 1000
            datePickerDialog.show()
        }
        binding.imgTodayDiary.clipToOutline = true

        binding.btnBack.setOnClickListener {
            dActivity.finish()
        }

        binding.btnAddDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, year, month, day)
        }
        binding.imgTodayDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, year, month, day)
        }

        binding.btnEdit.setOnClickListener {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnEdit.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
            binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
            binding.editTextTodayQuestionAnswer.isEnabled = true
        }
        binding.btnSave.setOnClickListener {
            binding.btnSave.visibility = View.GONE
            binding.btnEdit.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE
            binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
            binding.editTextTodayQuestionAnswer.isEnabled = false
        }

        return binding.root
    }

}