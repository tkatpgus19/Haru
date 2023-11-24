package com.ssafy.diary.api

import com.ssafy.diary.dto.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    // 개인 정보 수정
    @PUT("api/user/update")
    suspend fun update(@Body user: User): Response<Boolean>

    // 비밀번호 매칭
    @GET("api/user/match")
    suspend fun matchPassword(@Query("userId") userId: String, @Query("userPassword") userPassword: String): Response<User>

    // 하트 수 변경
    @PUT("api/user/updateHeart")
    suspend fun updateHeart(@Query("userId") userId: String, @Query("userHeart") userHeart: String): Response<Boolean>

    // 사용자 프로필 변경
    @Multipart
    @PUT("api/user/updateImage")
    suspend fun updateImage(@Part("userId") userId: String, @Part userImg: MultipartBody.Part): Response<Boolean>

    // 사용자 프로필 조회
    @GET("api/user/getImage")
    suspend fun getImage(@Query("userId") userId: String): Response<String>

    // 사용자 정보 조회
    @GET("api/user/getUserInfo")
    suspend fun getUserInfo(@Query("userId") userId: String): Response<User>
}