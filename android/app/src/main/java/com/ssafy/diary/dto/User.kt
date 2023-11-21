package com.ssafy.diary.dto

data class User(
    var userId: String,
    var userPassword: String,
    var userNickname: String,
    var userEmail: String,
    var userHeart: Int
){
    constructor(): this("", "", "", "", 0)
}
