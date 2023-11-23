package com.ssafy.diary.nav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.diary.MainActivity
import com.ssafy.diary.MainActivity.Companion.backgroundImg
import com.ssafy.diary.MainActivity.Companion.backgroundList
import com.ssafy.diary.MainActivity.Companion.characterImg
import com.ssafy.diary.MainActivity.Companion.characterList
import com.ssafy.diary.R
import com.ssafy.diary.adapter.InventoryAdapter
import com.ssafy.diary.databinding.FragmentMainBinding
import com.ssafy.diary.databinding.FragmentTodoListBinding
import com.ssafy.diary.dto.InventoryItem
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch
import java.util.Calendar

class TodoListFragment : Fragment() {
    private val binding by lazy { FragmentTodoListBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val setting = SharedPreferencesUtil(requireContext()).getSetting()
        backgroundImg = setting.background
        characterImg = setting.character

        userId = SharedPreferencesUtil(requireContext()).getUser().userId
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = "${year}-${month+1}-${day}"


        lifecycleScope.launch {
            val isHomeworkDone = RetrofitUtil.homeworkService.getHomework(userId, date).body()
            if(isHomeworkDone!!.userId != null){
                binding.checkboxTodayQuestion.setBackgroundResource(R.drawable.check_box_style2)
            } else{
                binding.checkboxTodayQuestion.setBackgroundResource(R.drawable.check_box_style)
            }

            val isDiaryDone = RetrofitUtil.diaryService.getDiary(userId, date).body()
            if(isDiaryDone!!.userId != null){
                binding.checkboxTodayDiary.setBackgroundResource(R.drawable.check_box_style2)
            } else{
                binding.checkboxTodayDiary.setBackgroundResource(R.drawable.check_box_style)
            }

            val inventoryItems = RetrofitUtil.inventoryService.getInventory(userId).body()!!

            val cItems = ArrayList<Int>()
            val bItems = ArrayList<Int>()
            inventoryItems.forEach {
                if(it.itemType == "B"){
                    bItems.add(it.itemId)
                } else{
                    cItems.add(it.itemId-5)
                }
            }
            val cAdapter = InventoryAdapter(requireContext(), cItems, characterList, "C")
            val bAdapter = InventoryAdapter(requireContext(), bItems, backgroundList, "B")
            binding.recyclerBack.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerBack.adapter = bAdapter
            binding.recyclerItem.adapter = cAdapter
        }

        binding.btnSaveItemType.setOnClickListener {
            SharedPreferencesUtil(requireContext()).saveSetting(backgroundImg, characterImg)
            Toast.makeText(requireContext(), "적용되었습니다.", Toast.LENGTH_SHORT).show()
        }
        binding.btnSaveBackType.setOnClickListener {
            SharedPreferencesUtil(requireContext()).saveSetting(backgroundImg, characterImg)
            Toast.makeText(requireContext(), "적용되었습니다.", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}