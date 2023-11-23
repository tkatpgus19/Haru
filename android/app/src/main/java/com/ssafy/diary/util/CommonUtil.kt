package com.ssafy.diary.util

import android.content.Context
import java.text.SimpleDateFormat

class CommonUtil(context: Context) {
    fun convertDateToTimestamp(date: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.parse(date).time
    }

    fun convertTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(timestamp)
    }
}