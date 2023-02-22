package com.example.notes.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notes.Model.Note
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao

    // Singleton
    companion object{
        private var INSTANCE: NoteDatabase? = null
        fun getDatabse(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}