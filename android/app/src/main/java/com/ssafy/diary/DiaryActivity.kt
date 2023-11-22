package com.ssafy.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ssafy.diary.databinding.ActivityDiaryBinding
import com.ssafy.diary.databinding.ActivityMainBinding
import com.ssafy.diary.diary.DiaryDetailsFragment
import com.ssafy.diary.diary.DiaryMainFragment
import com.ssafy.diary.nav.MainFragment
import com.ssafy.diary.nav.MyPageFragment
import com.ssafy.diary.nav.TodoListFragment
import java.util.Calendar

class DiaryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDiaryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        moveFragment(DIARY_MAIN_FRAGMENT, year.toString(), (month+1).toString(), day.toString())
    }


    // 프래그먼트 이동 함수
    fun moveFragment(destination: Int, year: String, month: String, day: String){
        val bundle = Bundle()
        bundle.putString("year", year)
        bundle.putString("month", month)
        bundle.putString("day", day)
        when(destination){
            0 ->
            {
                val fragment = DiaryMainFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            1 ->
            {
                val fragment = DiaryDetailsFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    // 프래그먼트 뒤로가기 구현
    fun goBack(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        supportFragmentManager.popBackStack()
    }

    companion object{
        const val DIARY_MAIN_FRAGMENT = 0
        const val DIARY_DETAILS_FRAGMENT = 1
    }
}