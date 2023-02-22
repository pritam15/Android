package com.example.jettrivia.repository

import android.util.Log
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.Questions
import com.example.jettrivia.model.QuestionsItem
import com.example.jettrivia.network.QuestionApi
import java.security.PrivateKey
import javax.inject.Inject

class JettriviaRepository @Inject constructor(
    private val api : QuestionApi

) {
    private val dataOrException
        = DataOrException<
            ArrayList<QuestionsItem>,
            Boolean ,
            Exception>()

    suspend fun getAllQuestion() : DataOrException<ArrayList<QuestionsItem>,Boolean,Exception>{

        try{
            dataOrException.loding = true
            dataOrException.data = api.getAllQuestion()
            if(dataOrException.data.toString().isNotEmpty())
                dataOrException.loding = false
        }
        catch (E : Exception){
            dataOrException.e = E
            Log.d("Exception", "${dataOrException.e!!.localizedMessage }")

        }
        return  dataOrException
    }
}