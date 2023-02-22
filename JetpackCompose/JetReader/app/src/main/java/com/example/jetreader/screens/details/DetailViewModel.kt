package com.example.jetreader.screens.details

import androidx.lifecycle.ViewModel
import com.example.jetreader.data.Resource
import com.example.jetreader.model.Item
import com.example.jetreader.repository.BookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepo) : ViewModel() {
    suspend fun getBookById(bookId: String) : Resource<Item>{
      return  repository.getBookById(bookId)
    }
}