package com.example.weatherforcast.data



/*
we wrap our weatherObject which we are reciving in the WeatherRepository
to make sure to also add other metadata like is loding if it's loding and exception
*/

// this is a wrapper Class to  add Extra metadata
class DataOrExcaption<T, Boolean, E : Exception>(
    var data : T? = null,
    var loding : Boolean? = null,
    var e : E? = null
) {
}