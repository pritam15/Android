package com.example.jetnoteapp.util

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {

    @TypeConverter
    fun timeStampFromDate(date : Date) :Long{
        return date.time
    }

    @TypeConverter
    fun dateFromTimeStamp(timeStamp : Long) : Date{

        return Date(timeStamp)
    }

}













