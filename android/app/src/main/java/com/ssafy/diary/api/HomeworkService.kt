package com.ssafy.diary.api

import com.ssafy.diary.dto.Homework
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface HomeworkService {
    // 숙제 조회
    @GET("api/homework/select")
    suspend fun getHomework(@Query("userId") userId: String, @Query("homeworkDate") homeworkDate: String): Response<Homework>

    // 숙제 저장
    @POST("api/homework/insert")
    suspend fun saveHomework(@Body homework: Homework): Response<Boolean>

    // 숙제 수정
    @PUT("api/homework/update")
    suspend fun updateHomework(@Body homework: Homework): Response<Boolean>

    // 숙제 삭제
    @DELETE("api/homework/delete")
    suspend fun deleteHomework(@Query("userId") userId: String, @Query("homeworkDate") homeworkDate: String): Response<Boolean>
}