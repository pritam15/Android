package com.example.jetreader.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun FormateDate(timeStamp : Timestamp) : String{
    val date = DateFormat.getDateInstance()
        .format(timeStamp.toDate())
        .toString().split(",")[0]

    return date
}