package com.ssafy.diary.api

import com.ssafy.diary.dto.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    // 아이디 중복 체크
    @GET("api/user/isUsed")
    suspend fun isUsed(@Query("userId") userId: String): Response<Boolean>

    // 로그인
    @POST("api/user/login")
    suspend fun login(@Body user: User): Response<User>

    // 회원가입
    @POST("api/user/join")
    suspend fun join(@Body user: User): Response<Boolean>

    // 아이디 조회
    @GET("api/user/findId")
    suspend fun findId(@Query("userEmail") userEmail: String): Response<String>

    // 계정 탈퇴
    @DELETE("api/user/unregister/{userId}")
    suspend fun deleteAccount(@Path("userId") userId: String): Response<Boolean>

    // 비밀번호 매칭
    @GET("api/user/match")
    suspend fun matchPassword(@Query("userId") userId: String, @Query("userPassword") userPassword: String): Response<Boolean>
}