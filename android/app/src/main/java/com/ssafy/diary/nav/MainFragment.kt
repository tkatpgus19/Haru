package com.ssafy.diary.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ssafy.diary.MainActivity
import com.ssafy.diary.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val mActivity by lazy { activity as MainActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Glide.with(this)
            .load(com.ssafy.diary.R.drawable.character02)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
            .into(binding.imgMainItem01)
        return binding.root
    }

}