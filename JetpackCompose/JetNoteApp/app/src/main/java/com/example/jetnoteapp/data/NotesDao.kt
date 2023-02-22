package com.example.jetnoteapp.data

import androidx.room.*
import com.example.jetnoteapp.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query(value = "Select * From notes_table")
    fun getAllNotes() : Flow<List<Notes>>

//    @Query("Select * From notes_table where id =:id")
//    suspend fun getNoteById(id : String) : Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note : Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note : Notes)

    @Query("Delete From notes_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note:Notes)
}
