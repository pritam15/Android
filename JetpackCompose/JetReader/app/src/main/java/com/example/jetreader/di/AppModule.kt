package com.example.jetreader.di

import com.example.jetreader.constant.Constant
import com.example.jetreader.network.BooksApi
import com.example.jetreader.repository.BookRepo
import com.example.jetreader.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun ProviesFireStoreRepository() = FirestoreRepository(query = FirebaseFirestore.getInstance()
        .collection("books"))

    @Singleton
    @Provides
    fun getRepository(api : BooksApi) = BookRepo(api)

    @Singleton
    @Provides
    fun provideBookApi() : BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

}