package com.example.notes.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.Model.Note

@Dao
interface NoteDao {

    @Insert
   suspend fun insert(note:Note)

    @Delete
    suspend  fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note_table  ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}