package com.example.notes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notes.Model.Note
import com.example.notes.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(){
    val allNotes : LiveData<List<Note>> = repository.allNotes

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun update(note:Note) = viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }

    fun delete(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
                return NoteViewModel(repository) as T
            }
            else{
                throw IllegalArgumentException("Unknown View Model")
            }
        }

    }

}