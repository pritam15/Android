package com.example.notes.View

import android.app.Application
import androidx.room.RoomDatabase
import com.example.notes.Repository.NoteRepository
import com.example.notes.Room.NoteDatabase

class NoteApplication: Application() {

    val database by lazy { NoteDatabase.getDatabse(this)}
    val repository by lazy { NoteRepository(database.getNoteDao()) }
}