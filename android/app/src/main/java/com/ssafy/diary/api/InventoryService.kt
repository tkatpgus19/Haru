package com.ssafy.diary.api

import com.ssafy.diary.dto.InventoryItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InventoryService {
    // 인벤토리 조회
    @GET("api/inventory/select")
    suspend fun getInventory(@Query("userId") userId: String): Response<ArrayList<InventoryItem>>

    // 인벤토리 아이템 추가
    @GET("api/inventory/insert")
    suspend fun addItem(@Query("userId") userId: String, @Query("itemId") itemId: String)
}