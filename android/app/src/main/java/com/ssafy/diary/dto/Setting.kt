package com.ssafy.diary.dto

data class Setting(
    var background: Int,
    var character: Int
){
    constructor(): this(0, 0)
}
