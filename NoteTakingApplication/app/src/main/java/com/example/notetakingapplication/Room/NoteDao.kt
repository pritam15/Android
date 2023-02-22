package com.example.notetakingapplication.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notetakingapplication.Model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
   suspend fun insert(note: Note)

    @Delete
   suspend fun delete(note: Note)

    @Update
   suspend fun update(note: Note)

   @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): Flow<List<Note>>

   @Query("DELETE FROM note_table")
    fun deleteAllNote()
}