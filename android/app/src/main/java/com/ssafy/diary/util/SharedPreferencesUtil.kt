package com.ssafy.diary.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.diary.R
import com.ssafy.diary.dto.Setting
import com.ssafy.diary.dto.User

class SharedPreferencesUtil (context: Context) {
    val SHARED_PREFERENCES_NAME = "haru_preference"

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    // 배경화면 세팅 저장
    fun saveSetting(background: Int, character: Int){
        val editor = preferences.edit()
        if(background == -1){
            editor.putInt("character", character)
        } else if(character == -1){
            editor.putInt("background", background)
        } else{
            editor.putInt("character", character)
            editor.putInt("background", background)
        }
        editor.apply()
    }
    fun getSetting(): Setting{
        val setting = Setting()
        setting.background = preferences.getInt("background", R.drawable.background01)
        setting.character = preferences.getInt("character", R.drawable.character01)
        return setting
    }

    //사용자 정보 저장
    fun addUser(user: User){
        val editor = preferences.edit()
        editor.putString("id", user.userId)
        editor.putString("nickname", user.userNickname)
        editor.putString("email", user.userEmail)
        editor.putInt("heart", user.userHeart)
        editor.apply()
    }

    // 사용자 하트 저장
    fun updateHeart(heart: Int){
        val editor = preferences.edit()
        editor.putInt("heart", heart)
        editor.apply()
    }

    fun getUser(): User{
        val id = preferences.getString("id", "")
        if (id != ""){
            val id = preferences.getString("id", "")
            val nickname = preferences.getString("nickname", "")
            val email = preferences.getString("email", "")
            val heart = preferences.getInt("heart", 0)
            return User(id!!, "", nickname!!, email!!, heart)
        }else{
            return User()
        }
    }

    fun deleteUser(){
        //preference 지우기
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    // 오늘의 질문 저장
    fun saveQuestion(){
        val editor = preferences.edit()
        editor.putInt("question", (0..5).random())
        editor.apply()
    }


}
