package com.example.notetakingapplication.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notetakingapplication.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getDao(): NoteDao

    //singleton
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        fun getDatabse(context: Context,scope: CoroutineScope): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class NoteDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{ noteDatabase ->
                scope.launch {
                    val noteDao = noteDatabase.getDao()

                    noteDao.insert(Note("title 1", "Description 1"))
                    noteDao.insert(Note("title 2", "Description 2"))
                    noteDao.insert(Note("title 3", "Description 3"))
                }
            }

        }
    }
}