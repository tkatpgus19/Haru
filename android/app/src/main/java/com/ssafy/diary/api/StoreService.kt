package com.ssafy.diary.api

import com.ssafy.diary.dto.Item
import retrofit2.Response
import retrofit2.http.GET

interface StoreService {
    // 상점 아이템 조회
    @GET("api/item/selectAll")
    suspend fun getItem(): Response<ArrayList<Item>>
}