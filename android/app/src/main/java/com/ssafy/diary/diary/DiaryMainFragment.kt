package com.ssafy.diary.diary

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_DETAILS_FRAGMENT
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.MAIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.dto.Homework
import com.ssafy.diary.util.CommonUtil
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class DiaryMainFragment : Fragment() {
    private val binding by lazy { FragmentDiaryMainBinding.inflate(layoutInflater) }
    private val dActivity by lazy { activity as DiaryActivity }
    private val questionList = arrayOf("요즘 내가 느끼는 감정은 무엇인가요?",
        "나는 요즘 어떤 얼굴 표정으로 살아가고 있나요?",
        "요즘 내 마음을 닮은 이미지는 무엇인가요?",
        "오늘 하루 나 자신을 조금 떨어져 관찰한다면, 어떤 모습인가요?",
        "오늘도 무사히 살아낸 나에게 어떤 말을 해주고 싶은가요?",
        "하루 중 가장 좋아하는 순간은 언제인가요?",
        "하루 중 내가 가장 좋아하는 시간대는 언제인가요?",
        "듣는 것만으로 나를 행복하게 해주는 말 한마디는 무엇인가요?",
        "오늘, 어떤 음식을 먹으면 행복할 것 같나요?",
        "내가 가장 좋아하는 색깔은 무엇인가요?",
        "나의 취미는 무엇인가요?",
        "나는 어떤 옷을 좋아하는 사람인가요?",
        "방에 액자를 딱 하나 걸 수 있다면, 어떤 사진을 걸고 싶은가요?",
        "나의 플레이 리스트는 무엇인가요?",
        "내 삶에서 가장 좋았던 여행은 언제였나요?",
        "나의 인생 영화는 무엇인가요?",
        "나와 가장 닮았다고 생각되는 영화 속 주인공은 누구인가요?",
        "만약 나 자신을 동물에 비유한다면, 어떤 동물일까요?",
        "나는 어떤 것에 몰입하는 사람인가요?",
        "하고 있는 것만으로도 행복한 일이 있나요?")
    private lateinit var question: String
    lateinit var userId: String
    lateinit var joinDate: String

    lateinit var year: String
    lateinit var month: String
    lateinit var day: String
    lateinit var date: String
    private var modify = false
    private val homework = Homework()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = arguments!!.getString("year")!!
        month = arguments!!.getString("month")!!
        day = arguments!!.getString("day")!!
        date = "${year}-${month}-${day}"

        val calendar = Calendar.getInstance()
        val now = calendar.timeInMillis
        val today = SharedPreferencesUtil(requireContext()).getQuestionDate()+ 86400000
        val tmp = SharedPreferencesUtil(requireContext()).getQuestion()
        if(tmp == -1 || now > today){
            SharedPreferencesUtil(requireContext()).saveQuestion(CommonUtil(requireContext()).convertDateToTimestamp(date))
            question = questionList[SharedPreferencesUtil(requireContext()).getQuestion()]
        } else{
            question = questionList[tmp]
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val userInfo = SharedPreferencesUtil(requireContext()).getUser()
        userId = userInfo.userId
        joinDate = userInfo.joinDate

        lifecycleScope.launch {




            val result = RetrofitUtil.homeworkService.getHomework(userId, date).body()
            if(result!!.userId != null){
                binding.textTodayQuestion.text = result.homeworkQuestion
                binding.btnSave.visibility = View.GONE
                binding.btnEdit.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
                binding.editTextTodayQuestionAnswer.isEnabled = false
                binding.editTextTodayQuestionAnswer.setText(result.homeworkContent)
                modify = true
            } else{

                binding.textTodayQuestion.text = "Q " + question
                binding.btnSave.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE
                binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                binding.editTextTodayQuestionAnswer.isEnabled = true
                modify = false
            }
        }


        binding.tvDate.text = date
        binding.tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val format = SimpleDateFormat("yyyy-MM-dd")
            val today = format.format(calendar.time)

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
                        binding.editTextTodayQuestionAnswer.isEnabled = false
                        binding.editTextTodayQuestionAnswer.setText(result.homeworkContent)
                        binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)

                        if(date == today){
                            binding.btnSave.visibility = View.GONE
                            binding.btnEdit.visibility = View.VISIBLE
                            binding.btnDelete.visibility = View.VISIBLE
                            modify = true
                        }
                        else{
                            binding.btnSave.visibility = View.GONE
                            binding.btnEdit.visibility = View.GONE
                            binding.btnDelete.visibility = View.GONE
                            modify = false
                        }
                    } else{
                        binding.textTodayQuestion.text = "Q " + question
                        binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                        if(date == today){
                            binding.editTextTodayQuestionAnswer.isEnabled = true
                            binding.btnSave.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.GONE
                            binding.btnDelete.visibility = View.GONE
                            modify = false
                        }
                        else{
                            binding.editTextTodayQuestionAnswer.isEnabled = false
                            binding.editTextTodayQuestionAnswer.setText("작성된 일기가 없습니다.")
                            binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
                            binding.btnSave.visibility = View.GONE
                            binding.btnEdit.visibility = View.GONE
                            binding.btnDelete.visibility = View.GONE
                            modify = false
                        }
                    }
                }
            }
            Log.d("뭔데", year+month+day)
            val datePickerDialog = DatePickerDialog(requireContext(), R.style.MySpinnerDatePickerStyle, dpListener, year.toInt(), month.toInt(), day.toInt())

            datePickerDialog.datePicker.minDate = CommonUtil(requireContext()).convertDateToTimestamp(joinDate)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis - 1000
            datePickerDialog.show()
        }

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
            lifecycleScope.launch {
                homework.homeworkContent = binding.editTextTodayQuestionAnswer.text.toString()
                homework.userId = userId
                homework.homeworkDate = date
                homework.homeworkQuestion = question
                if(!modify){
                    val result = RetrofitUtil.homeworkService.saveHomework(homework).body()
                    if(result!!){
                        Toast.makeText(requireContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show()
                        RetrofitUtil.userService.updateHeart(userId, (userInfo.userHeart+1).toString())
                        SharedPreferencesUtil(requireContext()).updateHeart(userInfo.userHeart+1)
                    }

                } else{
                    val result = RetrofitUtil.homeworkService.updateHomework(homework).body()
                    Toast.makeText(requireContext(), "수정 되었습니다", Toast.LENGTH_SHORT).show()
                }
            }

            binding.btnSave.visibility = View.GONE
            binding.btnEdit.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE
            binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.edit_text_back)
            binding.editTextTodayQuestionAnswer.isEnabled = false
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                val result = RetrofitUtil.homeworkService.deleteHomework(userId, date).body()
                if(result!!){
                    Toast.makeText(requireContext(), "삭제 되었습니다", Toast.LENGTH_SHORT).show()
                    binding.btnSave.visibility = View.VISIBLE
                    binding.btnEdit.visibility = View.GONE
                    binding.btnDelete.visibility = View.GONE
                    binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                    binding.editTextTodayQuestionAnswer.isEnabled = true

                    RetrofitUtil.userService.updateHeart(userId, (userInfo.userHeart-1).toString())
                    SharedPreferencesUtil(requireContext()).updateHeart(userInfo.userHeart-1)
                }
            }
        }

        return binding.root
    }

}