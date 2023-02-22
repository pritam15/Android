package com.example.jettrivia.di

import com.example.jettrivia.network.QuestionApi
import com.example.jettrivia.repository.JettriviaRepository
import com.example.jettrivia.util.Constants
import com.example.jettrivia.util.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getQuestionRepository(api: QuestionApi) = JettriviaRepository(api)


    @Singleton
    @Provides
    fun provideQuestionApi() : QuestionApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)

    }


}

















