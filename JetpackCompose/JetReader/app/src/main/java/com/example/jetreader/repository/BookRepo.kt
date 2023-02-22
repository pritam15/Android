package com.example.jetreader.repository

import com.example.jetreader.data.Resource
import com.example.jetreader.model.Item
import com.example.jetreader.network.BooksApi
import javax.inject.Inject


class BookRepo @Inject constructor(private val api : BooksApi) {

    suspend fun getBooks(searchQuery : String) : Resource<List<Item>>{
         return try{
             Resource.Loading(data = true)
            val itemList = api.getAllBook(searchQuery).items
             if(itemList.isNotEmpty()) Resource.Loading(false)
            Resource.Sucess(data = itemList)
        }
        catch (e : Exception){
            Resource.Error(message = "An Error Occured ${e.localizedMessage}")
        }
    }

    suspend fun getBookById(bookID : String) : Resource<Item>{
        val response = try{
            Resource.Loading(data = true)
            api.getBookInfo(bookID)
        }
        catch (e : Exception){
         return Resource.Error(message = "An Error Occured ${e.localizedMessage}")
        }
        Resource.Loading(false)
        return Resource.Sucess(data = response)
    }

}