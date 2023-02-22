package com.example.notes.Model

import android.accounts.AuthenticatorDescription
import android.icu.text.CaseMap.Title
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(val title: String, val description: String) {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    @ColumnInfo(name = "dateTime")
    var time : Long = 0
}