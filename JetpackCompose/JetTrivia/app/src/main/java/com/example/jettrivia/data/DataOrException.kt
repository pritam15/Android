package com.example.jettrivia.data

data class DataOrException<T, Boolean, E: Exception>(
    var data : T? = null,
    var loding: Boolean? = null,
    var e : E? = null
)