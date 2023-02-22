package com.example.weatherforcast.network

import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.util.Constant
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET(value = "v1/forecast.json")
    suspend fun getWeather(
        @Query(value = "q") query : String = "london",
        @Query(value = "key") key : String = Constant.API_KEY,
        @Query(value = "days") days: String = "7",
        @Query(value = "aqi") aqi : String = "yes",
        @Query(value = "alerts") alerts: String = "yes"
    ) : Weather

}