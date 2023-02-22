package com.example.jetnoteapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

@Entity(tableName = "notes_table")
data class Notes (
    @PrimaryKey
    @ColumnInfo(name = "notes_id")
    val id : UUID = UUID.randomUUID(),

    @ColumnInfo(name = "notes_title")
    val title: String,

    @ColumnInfo(name = "notes_description")
    val description: String,


    @ColumnInfo(name = "notes_save_date")
    val saveDate : Date? = Date.from(Instant.now())
)







