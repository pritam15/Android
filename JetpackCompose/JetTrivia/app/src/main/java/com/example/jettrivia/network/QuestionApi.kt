package com.example.jettrivia.network

import com.example.jettrivia.model.Questions
import com.example.jettrivia.util.Constants.BASE_URL
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET(BASE_URL)
    suspend fun getAllQuestion() : Questions
}