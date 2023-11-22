package com.ssafy.diary.dto

data class Item(
    var itemId: Int,
    var itemType: String,
    var itemSource: String,
    var itemPrice: Int
) {
    constructor(): this(0, "", "", 0)
}