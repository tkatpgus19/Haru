package com.ssafy.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ssafy.diary.databinding.ActivityLoginBinding
import com.ssafy.diary.databinding.ActivityMainBinding
import com.ssafy.diary.diary.DiaryDetailsFragment
import com.ssafy.diary.diary.DiaryMainFragment
import com.ssafy.diary.login.FindIdFragment
import com.ssafy.diary.login.JoinFragment
import com.ssafy.diary.login.LoginFragment
import com.ssafy.diary.login.OpenFragment
import com.ssafy.diary.nav.MainFragment
import com.ssafy.diary.nav.MyPageFragment
import com.ssafy.diary.nav.TodoListFragment
import com.ssafy.diary.util.SharedPreferencesUtil

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // sharedPreference에 회원 정보가 저장되어 있으면 바로 서비스 연결
        val userInfo = SharedPreferencesUtil(this).getUser()
        if(userInfo.userId != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else{
            moveFragment(OPEN_FRAGMENT)
        }
    }

    // 프래그먼트 이동 함수
    fun moveFragment(destination: Int){
        when(destination){
            0 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, OpenFragment())
                .commit()
            1 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
            2 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, JoinFragment())
                .addToBackStack(null)
                .commit()
            3 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, FindIdFragment())
                .addToBackStack(null)
                .commit()
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
        const val OPEN_FRAGMENT = 0
        const val LOGIN_FRAGMENT = 1
        const val JOIN_FRAGMENT = 2
        const val FIND_ID_FRAGMENT = 3
    }
}