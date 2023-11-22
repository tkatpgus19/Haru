package com.ssafy.diary.util

import com.ssafy.diary.api.DiaryService
import com.ssafy.diary.api.HomeworkService
import com.ssafy.diary.api.InventoryService
import com.ssafy.diary.api.ItemService
import com.ssafy.diary.api.UserService
import com.ssafy.diary.config.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService = ApplicationClass.retrofit.create(UserService::class.java)
        val diaryService = ApplicationClass.retrofit.create(DiaryService::class.java)
        val homeworkService = ApplicationClass.retrofit.create(HomeworkService::class.java)
        val inventoryService = ApplicationClass.retrofit.create(InventoryService::class.java)
        val itemService = ApplicationClass.retrofit.create(ItemService::class.java)
    }
}