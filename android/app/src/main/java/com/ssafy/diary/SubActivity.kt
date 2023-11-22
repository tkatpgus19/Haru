package com.ssafy.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.diary.databinding.ActivityMainBinding
import com.ssafy.diary.databinding.ActivitySubBinding
import com.ssafy.diary.mypage.MyInfoFragment
import com.ssafy.diary.nav.MainFragment
import com.ssafy.diary.nav.MyPageFragment
import com.ssafy.diary.nav.TodoListFragment
import com.ssafy.diary.store.StoreFragment

class SubActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        if(type == "store"){
            moveFragment(STORE_FRAGMENT)
        } else{
            moveFragment(MYPAGE_FRAGMENT)
        }

    }

    // 프래그먼트 이동 함수
    fun moveFragment(destination: Int){
        when(destination){
            0 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, StoreFragment())
                .commit()
            1 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, MyPageFragment())
                .commit()
        }

    }
    companion object {
        const val STORE_FRAGMENT = 0
        const val MYPAGE_FRAGMENT = 1
    }
}