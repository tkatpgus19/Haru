package com.ssafy.diary.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.diary.dto.User

class SharedPreferencesUtil (context: Context) {
    val SHARED_PREFERENCES_NAME = "haru_preference"

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //사용자 정보 저장
    fun addUser(user: User){
        val editor = preferences.edit()
        editor.putString("id", user.userId)
        editor.putString("nickname", user.userNickname)
        editor.putString("email", user.userEmail)
        editor.putInt("heart", user.userHeart)
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


}
