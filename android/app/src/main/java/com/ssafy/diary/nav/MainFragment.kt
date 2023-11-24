package com.ssafy.diary.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ssafy.diary.databinding.FragmentMainBinding
import com.ssafy.diary.util.SharedPreferencesUtil


class MainFragment : Fragment() {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val setting = SharedPreferencesUtil(requireContext()).getSetting()
        Glide.with(this)
            .load(setting.character)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
            .into(binding.imgMainItem01)

        Glide.with(this)
            .load(setting.background)
            .into(binding.imgMainBack01)

        return binding.root
    }

}