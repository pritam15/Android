package com.example.weatherforcast.repository

import android.util.Log
import com.example.weatherforcast.data.DataOrExcaption
import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherAPI){
    /*
    we wrap our weatherObject which we are reciving in the WeatherRepository
    to make sure to also add other metadata like is loding if it's loding and exception
    */
    suspend fun getWeather(cityQuery : String) :
            DataOrExcaption<Weather, Boolean , Exception>{

        val response = try {
//            Log.d("####", "Inside Try Block")
            api.getWeather(query = cityQuery)
//            Log.d("####", "Inside Try Block 1")


        }
        catch (e : Exception){
            Log.d("####", "WeatherRepository: ${e}")

         return DataOrExcaption(e = e)
        }
        Log.d("####", "WeatherRepository: ${api.getWeather()}")
        Log.d("####", "WeatherReopsitory: ${response}")
        return DataOrExcaption(data = response)
    }
}