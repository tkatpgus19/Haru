package com.ssafy.diary.diary

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_MAIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryDetailsBinding
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.dto.Diary
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.Date

class DiaryDetailsFragment : Fragment() {
    private val binding by lazy { FragmentDiaryDetailsBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }
    lateinit var userId: String
    lateinit var date: String
    private var modify = false

    lateinit var year: String
    lateinit var month: String
    lateinit var day: String

    private var todayFeeling = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = arguments!!.getString("year")!!
        month = arguments!!.getString("month")!!
        day = arguments!!.getString("day")!!
        date = "${year}-${month}-${day}"
        binding.tvDate.text = date
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val emotions =
            mapOf<TextView, String>(binding.feeling01 to "\uD83D\uDE0A", binding.feeling02 to "\uD83D\uDE06", binding.feeling03 to "\uD83D\uDE02", binding.feeling04 to "\uD83D\uDE25",
                binding.feeling05 to "\uD83D\uDE2D", binding.feeling06 to "\uD83D\uDE14", binding.feeling07 to "\uD83D\uDE24", binding.feeling08 to "\uD83D\uDE21",
                binding.feeling09 to "\uD83E\uDD2C", binding.feeling10 to "\uD83E\uDD12", )

        userId = SharedPreferencesUtil(requireContext()).getUser().userId

        binding.btnBack.setOnClickListener {
            dActivity.goBack(this)
        }

        binding.editTextTodayDiary.movementMethod = ScrollingMovementMethod.getInstance()
        binding.tvDate.text = date

        lifecycleScope.launch {
            val result = RetrofitUtil.diaryService.getDiary(userId, date).body()
            if(result!!.userId == null){
                binding.feeling.visibility = View.GONE
                binding.emotionContainer.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.INVISIBLE
                binding.btnEdit.visibility = View.INVISIBLE
                binding.editTextTodayDiary.isEnabled = true
                binding.btnUpload.visibility = View.VISIBLE
            } else{
                binding.feeling.visibility = View.VISIBLE
                binding.feeling.text = result.diaryEmotion
                binding.emotionContainer.visibility = View.GONE
                binding.editTextTodayDiary.setText(result.diaryContent)
                binding.btnSave.visibility = View.INVISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.VISIBLE
                todayFeeling = result.diaryEmotion
                binding.editTextTodayDiary.isEnabled = false
            }
        }

        binding.btnSave.setOnClickListener {
            if(binding.editTextTodayDiary.text.isNotEmpty()){
                lifecycleScope.launch {
                    val diary = Diary()
                    diary.diaryContent = binding.editTextTodayDiary.text.toString()
                    diary.userId = userId
                    diary.diaryDate = date
                    diary.diaryEmotion = todayFeeling
                    if(!modify) {
                        val result = RetrofitUtil.diaryService.saveDiary(diary).body()
                        if (result!!) {
                            Toast.makeText(requireContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show()

                            binding.feeling.visibility = View.VISIBLE
                            binding.emotionContainer.visibility = View.GONE
                            binding.btnSave.visibility = View.INVISIBLE
                            binding.btnDelete.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.VISIBLE
                            binding.editTextTodayDiary.isEnabled = false
                        }
                    } else{
                        val result = RetrofitUtil.diaryService.updateDiary(diary).body()
                        if(result!!){
                            Toast.makeText(requireContext(), "수정 되었습니다", Toast.LENGTH_SHORT).show()

                            binding.feeling.visibility = View.VISIBLE
                            binding.emotionContainer.visibility = View.GONE
                            binding.btnSave.visibility = View.INVISIBLE
                            binding.btnDelete.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.VISIBLE
                            binding.editTextTodayDiary.isEnabled = false
                        }
                    }
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            lifecycleScope.launch {
                modify = true

                binding.btnUpload.visibility = View.VISIBLE
                binding.emotionContainer.visibility = View.VISIBLE
                binding.btnSave.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.INVISIBLE
                binding.btnEdit.visibility = View.INVISIBLE

                binding.editTextTodayDiary.isEnabled = true
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                val result = RetrofitUtil.diaryService.deleteDiary(userId, date).body()
                if(result!!){
                    Toast.makeText(requireContext(), "삭제 되었습니다", Toast.LENGTH_SHORT).show()
                    dActivity.goBack(this@DiaryDetailsFragment)
                }
            }
        }

        emotions.forEach {
            val key = it.key
            it.key.setOnClickListener {
                todayFeeling = emotions[key]!!
                binding.feeling.text = todayFeeling
                binding.feeling.visibility = View.VISIBLE

            }
        }

        return binding.root
    }

}