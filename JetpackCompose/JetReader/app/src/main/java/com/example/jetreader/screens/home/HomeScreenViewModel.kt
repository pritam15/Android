package com.example.jetreader.screens.home

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetreader.data.DataOrException
import com.example.jetreader.model.MBook
import com.example.jetreader.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FirestoreRepository) : ViewModel() {
        val data : MutableState<DataOrException<List<MBook>,Boolean,Exception>>
        = mutableStateOf(
            DataOrException(listOf(), true, Exception())
        )

    init {
        getAllBookFromDatabase()
    }

    private fun getAllBookFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromFirestore()
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("Data" ,"Data recived from database ${data.value.data?.toList().toString()}" )
    }

}