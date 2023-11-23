package com.ssafy.diary.api

import com.ssafy.diary.dto.Diary
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface DiaryService {
    // 일기 조회
    @GET("api/diary/select")
    suspend fun getDiary(@Query("userId") userId: String, @Query("diaryDate") diaryDate: String): Response<Diary>

    // 일기 저장
    @Multipart
    @POST("api/diary/insert")
    suspend fun saveDiary(@Part("diary") diary: Diary, @Part diaryImg: MultipartBody.Part): Response<Boolean>

    //  일기 수정
    @Multipart
    @PUT("api/diary/update")
    suspend fun updateDiary(@Part("diary") diary: Diary, @Part diaryImg: MultipartBody.Part): Response<Boolean>

    // 일기 삭제
    @DELETE("api/diary/delete")
    suspend fun deleteDiary(@Query("userId") userId: String, @Query("diaryDate") diaryDate: String): Response<Boolean>


}