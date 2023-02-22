package com.example.jetnoteapp.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnoteapp.model.Notes
import com.example.jetnoteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {

    private val _noteList = MutableStateFlow<List<Notes>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getNotesList().distinctUntilChanged()
                .collect{ listNotes ->
                    if(listNotes.isEmpty()){
                        Log.d("Empty" ,": List Was Empty")
                    }
                    else{
                        _noteList.value = listNotes
                    }
                }
        }
        Log.d("_notesList" , "::::${_noteList.value.size}")
    }

    fun addNote(note: Notes) = viewModelScope.launch { repository.addNote(note) }
    fun removeNote(note: Notes) = viewModelScope.launch { repository.deleNote(note) }
    fun removeAll() = viewModelScope.launch { repository.deleteAllNote() }
    fun updateNote(note:Notes) = viewModelScope.launch { repository.updateNote(note) }



}