package com.ssafy.diary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.diary.MainActivity.Companion.backgroundList
import com.ssafy.diary.MainActivity.Companion.characterList
import com.ssafy.diary.SubActivity
import com.ssafy.diary.adapter.StoreAdapter
import com.ssafy.diary.databinding.FragmentStoreBinding
import com.ssafy.diary.dto.InventoryItem
import com.ssafy.diary.dto.Item
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class StoreFragment : Fragment() {
    private val binding by lazy { FragmentStoreBinding.inflate(layoutInflater) }
    private val sActivity by lazy { activity as SubActivity }

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
        var itemList = ArrayList<Item>()
        var cItems = ArrayList<Item>()
        var bItems = ArrayList<Item>()
        var cInvItems = ArrayList<InventoryItem>()
        var bInvItems = ArrayList<InventoryItem>()
        var inventoryItems = ArrayList<InventoryItem>()

        binding.btnBack.setOnClickListener {
            sActivity.finish()
        }

        // 아이템 목록을 받아 분류하고 리사이클러 뷰에 등록
        lifecycleScope.launch {
            itemList = RetrofitUtil.storeSerivce.getItem().body()!!
            itemList.forEach {
                if(it.itemType == "B"){
                    bItems.add(it)
                } else{
                    it.itemId -= 5
                    cItems.add(it)
                }
            }

            inventoryItems = RetrofitUtil.inventoryService.getInventory(userInfo.userId).body()!!
            inventoryItems.forEach {
                if(it.itemType == "B"){
                    bInvItems.add(it)
                } else{
                    it.itemId -= 5
                    cInvItems.add(it)
                }
            }

            val cAdapter = StoreAdapter(binding, requireContext(), cInvItems, cItems, characterList, "C")
            binding.recyclerItem.adapter = cAdapter
            binding.recyclerItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            val bAdapter = StoreAdapter(binding, requireContext(), bInvItems, bItems, backgroundList, "B")
            binding.recyclerBack.adapter = bAdapter
            binding.recyclerBack.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }

}