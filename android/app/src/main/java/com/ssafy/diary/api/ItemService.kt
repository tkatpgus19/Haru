package com.ssafy.diary.api

import com.ssafy.diary.dto.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    // 상품목록 조회

    @GET("api/item/selectAll")
    suspend fun getItem(): Response<ArrayList<Item>>
}