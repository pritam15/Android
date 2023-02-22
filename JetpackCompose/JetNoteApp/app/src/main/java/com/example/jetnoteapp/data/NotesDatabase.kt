package com.example.jetnoteapp.data;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetnoteapp.model.Notes
import com.example.jetnoteapp.util.DateTypeConverter
import com.example.jetnoteapp.util.UUIDConverter

@Database(entities = [Notes::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, UUIDConverter::class)
abstract class NoteDatabase : RoomDatabase(){
      abstract fun getDao() : NotesDao
    }
