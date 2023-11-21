package com.ssafy.diary.dto

data class Homework(
    var homeworkNo: Int,
    var userId: String,
    var homeworkQuestion: String,
    var homeworkContent: String,
    var homeworkDate: String
)
