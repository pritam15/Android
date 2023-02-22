package com.example.notetakingapplication.ViewModel

import androidx.lifecycle.*
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NoteRepository): ViewModel() {
    val allNotes: LiveData<List<Note>> = repository.allNote.asLiveData()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }
    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException("Unknown View Model")
        }
    }

}