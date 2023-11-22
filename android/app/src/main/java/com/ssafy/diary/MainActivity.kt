package com.ssafy.diary

import android.content.Intent
import android.graphics.Path.Op
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ssafy.diary.databinding.ActivityMainBinding
import com.ssafy.diary.dto.User
import com.ssafy.diary.mypage.MyInfoFragment
import com.ssafy.diary.nav.MainFragment
import com.ssafy.diary.nav.MyPageFragment
import com.ssafy.diary.nav.TodoListFragment
import com.ssafy.diary.util.SharedPreferencesUtil

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그아웃 하지 않아 sharedPreference에 회원 정보가 저장되어 있으면 바로 서비스 연결
        val userInfo = SharedPreferencesUtil(this).getUser()
        if(userInfo.userId != ""){
            moveFragment(MAIN_FRAGMENT)
        } else{
            finish()
        }

        // 하단 네비게이션 바
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_one -> moveFragment(MAIN_FRAGMENT)
                R.id.action_two -> moveFragment(TODO_LIST_FRAGMENT)
                else -> moveFragment(MYPAGE_FRAGMENT)
            }
            true
        }

        binding.btnGoToDiary.setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }

        binding.btnMenu.setOnClickListener {
            binding.layoutMain.open()
        }

        binding.inViewDrawer.btnStore.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("type", "store")
            startActivity(intent)
        }
        binding.inViewDrawer.textName.text = userInfo.userNickname
        binding.inViewDrawer.textHeartCount.text = "${userInfo.userHeart}개"
        binding.inViewDrawer.btnLogout.setOnClickListener {
            SharedPreferencesUtil(this).deleteUser()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    // 프래그먼트 이동 함수
    fun moveFragment(destination: Int){
        when(destination){
            0 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, MainFragment())
                .commit()
            1 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, TodoListFragment())
                .commit()
            2 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, MyPageFragment())
                .commit()
            3 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_container, MyInfoFragment())
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
        const val MAIN_FRAGMENT = 0
        const val TODO_LIST_FRAGMENT = 1
        const val MYPAGE_FRAGMENT = 2
        const val MYINFO_PAGE_FRAGMENT = 3

        lateinit var bCheckbox: TextView
        lateinit var cCheckbox: TextView
        var backgroundImg = -1
        var characterImg = -1
        val characterList = listOf(R.drawable.character01, R.drawable.character02, R.drawable.character03, R.drawable.character04, R.drawable.character05, R.drawable.character06)
        val backgroundList = listOf(R.drawable.background01, R.drawable.background02, R.drawable.background03, R.drawable.background04, R.drawable.background05)
    }

}