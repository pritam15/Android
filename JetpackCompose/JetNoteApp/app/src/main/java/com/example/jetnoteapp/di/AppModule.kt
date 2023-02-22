package com.example.jetnoteapp.di

import android.content.Context
import androidx.room.Room
import com.example.jetnoteapp.data.NoteDatabase
import com.example.jetnoteapp.data.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase : NoteDatabase) : NotesDao = noteDatabase.getDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context : Context) : NoteDatabase{
       return Room.databaseBuilder(context,NoteDatabase::class.java,"notes_db")
            .fallbackToDestructiveMigration()
            .build()
    }}
