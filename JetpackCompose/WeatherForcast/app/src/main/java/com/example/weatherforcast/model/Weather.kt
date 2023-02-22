package com.example.weatherforcast.model

data class Weather(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)