package com.ssafy.diary.dto

data class Diary(
    var diaryNo: Int,
    var userId: String,
    var diaryEmotion: String,
    var diaryContent: String,
    var diaryDate: String,
    var diaryImg: String
){
    constructor(): this(0, "", "", "", "", "")
}
