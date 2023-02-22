package com.example.jetnoteapp.repository

import com.example.jetnoteapp.data.NotesDao
import com.example.jetnoteapp.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private  val noteDao : NotesDao) {

    suspend fun addNote(note: Notes) = noteDao.addNote(note)
    suspend fun updateNote(note: Notes) = noteDao.updateNote(note)
    suspend fun deleNote(note: Notes) = noteDao.deleteNote(note)
    suspend fun deleteAllNote() = noteDao.deleteAll()

    fun getNotesList() : Flow<List<Notes>> = noteDao.getAllNotes().flowOn(Dispatchers.IO)
        .conflate()
}