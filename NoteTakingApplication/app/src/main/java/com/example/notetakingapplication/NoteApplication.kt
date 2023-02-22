package com.example.notetakingapplication

import android.app.Application
import com.example.notetakingapplication.Repository.NoteRepository
import com.example.notetakingapplication.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NoteDatabase.getDatabse(this,applicationScope) }
    val repositort by lazy { NoteRepository(database.getDao()) }
}