package com.ssafy.diary.util

import com.ssafy.diary.api.DiaryService
import com.ssafy.diary.api.UserService
import com.ssafy.diary.config.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService = ApplicationClass.retrofit.create(UserService::class.java)
        val diaryService = ApplicationClass.retrofit.create(DiaryService::class.java)
    }
}