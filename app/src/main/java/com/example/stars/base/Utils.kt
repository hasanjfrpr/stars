package com.example.stars.base

import java.text.DecimalFormat


public fun formatNumber(number:Double):String{
    val decimalFormat = DecimalFormat("#,###.##")

    val strFormat = decimalFormat.format(number)

    return strFormat
        .replace("0", "۰")
        .replace("1", "۱")
        .replace("2", "۲")
        .replace("3", "۳")
        .replace("4", "۴")
        .replace("5", "۵")
        .replace("6", "۶")
        .replace("7", "۷")
        .replace("8", "۸")
        .replace("9", "۹")
}