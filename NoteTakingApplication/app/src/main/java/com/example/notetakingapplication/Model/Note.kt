package com.example.notetakingapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(val title:String, val descripton: String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}