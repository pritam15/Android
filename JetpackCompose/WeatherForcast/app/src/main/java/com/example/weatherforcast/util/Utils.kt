package com.example.weatherforcast.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun DayFormater(timestamp: Int) : String{
    val sdf = SimpleDateFormat("EEEE")
    val day = Date(timestamp.toLong() * 1000)

    return sdf.format(day)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("h:mm:aa")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun getTime(timestamp: Int) : LocalTime{
    val instant = Instant.ofEpochSecond(timestamp.toLong())
    val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()

    return localTime
}