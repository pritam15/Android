package com.example.notetakingapplication.Repository

import androidx.annotation.WorkerThread
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.Room.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNote: Flow<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note){
        noteDao.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    @WorkerThread
    fun deleteAll(){
        noteDao.deleteAllNote()
    }
}