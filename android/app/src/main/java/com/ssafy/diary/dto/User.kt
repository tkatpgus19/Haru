package com.ssafy.diary.dto

import java.sql.Timestamp

data class User(
    var userId: String,
    var userPassword: String,
    var userNickname: String,
    var userEmail: String,
    var userHeart: Int,
    var joinDate: String,
    var userImg: String
){
    constructor(): this("", "", "", "", 0, "", "")
}
