package com.example.jetreader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetreader.data.Resource
import com.example.jetreader.model.Item
import com.example.jetreader.repository.BookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val bookRepo: BookRepo): ViewModel() {

    var listOfItem : List<Item> by mutableStateOf(listOf())
    var isLoading : Boolean by mutableStateOf(true)

    init{
        loadBook()
    }

    private fun loadBook() {
        SearchBook("android")
    }

    fun SearchBook(searchQuery: String){
        isLoading = true
        viewModelScope.launch(Dispatchers.Default) {

            if(searchQuery.isEmpty()){
                return@launch
            }
            try{
                when(val response = bookRepo.getBooks(searchQuery)){
                    is Resource.Sucess -> {
                        listOfItem = response.data!!
                        if (listOfItem.isNotEmpty())
                            isLoading = false
                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.d("Exception", "@ViewModel : ${response.message}")
                    }
                    else -> {
                        isLoading = false
                    }
                }
            }
            catch (e : Exception){
                isLoading = false
                Log.d("Exception", "@ViewModel : ${e.localizedMessage}")
            }
        }
    }
}
