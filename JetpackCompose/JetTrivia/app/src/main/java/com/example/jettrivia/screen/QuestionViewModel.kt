package com.example.jettrivia.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.QuestionsItem
import com.example.jettrivia.repository.JettriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository : JettriviaRepository) : ViewModel() {

    val data : MutableState<DataOrException<ArrayList<QuestionsItem>,
                Boolean,Exception>> = mutableStateOf(
            DataOrException(null,true,Exception("")))

    init {
        getAllQuestion()
    }

    fun getAllQuestion(){

        viewModelScope.launch {
            data.value.loding = true
            data.value = repository.getAllQuestion()

            if(data.value.data.toString().isNotEmpty() )
                data.value.loding = false

        }
    }

    fun getTotalCount() : Int{
        return data.value.data?.toMutableList()?.size!!
    }
}