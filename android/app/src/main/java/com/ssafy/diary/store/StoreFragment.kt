package com.ssafy.diary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.diary.MainActivity.Companion.characterList
import com.ssafy.diary.R
import com.ssafy.diary.adapter.ItemAdapter
import com.ssafy.diary.databinding.FragmentMyPageBinding
import com.ssafy.diary.databinding.FragmentStoreBinding
import com.ssafy.diary.dto.Item
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

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
        var list = ArrayList<Item>()
        lifecycleScope.launch {
            list = RetrofitUtil.itemService.getItem().body()!!
        }

        val adapter = ItemAdapter(requireContext(), emptyList(),characterList,  "C")
        binding.recyclerItem.adapter = adapter
        binding.recyclerItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        return binding.root
    }

}