package com.example.weatherforcast.screen.main

import androidx.lifecycle.ViewModel
import com.example.weatherforcast.data.DataOrExcaption
import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ViewModel @Inject constructor( private val repository : WeatherRepository ) :
    ViewModel() {
       suspend fun getWeatherData(city : String) : DataOrExcaption<Weather, Boolean, Exception>{
           return repository.getWeather(cityQuery = city)
       }
    }

