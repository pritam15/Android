package com.example.jetreader.repository

import com.example.jetreader.data.DataOrException
import com.example.jetreader.data.Resource
import com.example.jetreader.model.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val query : com.google.firebase.firestore.Query){
    suspend fun getAllBooksFromFirestore(): DataOrException<List<MBook>,Boolean,Exception>{
      val dataOrException = DataOrException<List<MBook>,Boolean,Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = query.get().await().documents.map{ documentSnapshot ->
                        documentSnapshot.toObject(MBook::class.java)!!
            }
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        }
        catch (ex : FirebaseFirestoreException){
            dataOrException.e = ex
        }

        return dataOrException
    }
}