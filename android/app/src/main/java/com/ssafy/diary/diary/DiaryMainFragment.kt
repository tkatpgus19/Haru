package com.ssafy.diary.diary

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ssafy.diary.DiaryActivity
import com.ssafy.diary.DiaryActivity.Companion.DIARY_DETAILS_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentDiaryMainBinding
import com.ssafy.diary.dto.Homework
import com.ssafy.diary.util.CommonUtil
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

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

        // 날짜가 넘어갔을 경우 새로운 질문으로 갱신
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

        // 뒷 배경 로드
        Glide.with(binding.imgQuestionBack)
            .load(R.drawable.today_question_back)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(binding.imgQuestionBack)
        Glide.with(binding.imgTodayDiary)
            .load(R.drawable.img_diary)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(binding.imgTodayDiary)

        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today = format.format(calendar.time)
        
        lifecycleScope.launch {
            val result = RetrofitUtil.homeworkService.getHomework(userId, date).body()
            // 작성된 숙제가 있을 때
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
                // 작성된 숙제가 없을 때
                if(date != today) {
                    binding.textTodayQuestion.text = "해당 날짜의 질문에 답하지 않았습니다"
                }else{
                    binding.textTodayQuestion.text = "Q "+ question
                }
                binding.btnSave.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE
                binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
                binding.editTextTodayQuestionAnswer.isEnabled = true
                modify = false
            }
        }

        binding.tvDate.text = date

        // 날짜를 선택해 특정 날짜의 숙제 조회
        binding.tvDate.setOnClickListener {
            val dpListener = DatePickerDialog.OnDateSetListener { view, y, m, d ->
                year = y.toString()
                month = (m+1).toString()
                day = d.toString()
                date = "${y}-${m+1}-${"%02d".format(d)}"
                binding.tvDate.text = date
                lifecycleScope.launch {
                    val result = RetrofitUtil.homeworkService.getHomework(userId, date).body()
                    if(result!!.userId != null){
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
                            binding.editTextTodayQuestionAnswer.setText("")
                            binding.editTextTodayQuestionAnswer.isEnabled = true
                            binding.btnSave.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.GONE
                            binding.btnDelete.visibility = View.GONE
                            modify = false
                        }
                        else{
                            binding.editTextTodayQuestionAnswer.isEnabled = false
                            binding.textTodayQuestion.text = "해당 날짜의 질문에 답하지 않았습니다"
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
            val datePickerDialog = DatePickerDialog(requireContext(), R.style.MySpinnerDatePickerStyle, dpListener, year.toInt(), month.toInt(), day.toInt())

            datePickerDialog.datePicker.minDate = CommonUtil(requireContext()).convertDateToTimestamp(joinDate)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis - 1000
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.darkBrown))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.darkBrown))
        }

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            dActivity.finish()
        }

        // 일기 작성 페이지로 이동
        binding.btnAddDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, year, month, day)
        }

        // 일기 작성 페이지로 이동
        binding.imgTodayDiary.setOnClickListener {
            dActivity.moveFragment(DIARY_DETAILS_FRAGMENT, year, month, day)
        }

        // 숙제 수정 버튼
        binding.btnEdit.setOnClickListener {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnEdit.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
            binding.editTextTodayQuestionAnswer.setBackgroundResource(R.drawable.search_box_style)
            binding.editTextTodayQuestionAnswer.isEnabled = true
        }
        
        // 숙제 저장 버튼
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                homework.homeworkContent = binding.editTextTodayQuestionAnswer.text.toString()
                homework.userId = userId
                homework.homeworkDate = date
                homework.homeworkQuestion = question
                // 신규 작성(저장) 일 때
                if(!modify){
                    val result = RetrofitUtil.homeworkService.saveHomework(homework).body()
                    if(result!!){
                        Toast.makeText(requireContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show()
                        RetrofitUtil.userService.updateHeart(userId, (userInfo.userHeart+1).toString())
                        SharedPreferencesUtil(requireContext()).updateHeart(userInfo.userHeart+1)
                    }
                }
                // 수정 일 때
                else{
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

        // 삭제 버튼
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