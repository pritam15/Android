package com.example.retrofitproject

import com.google.gson.annotations.SerializedName

//Model Class 
data class Posts(
    val id : Int,
    val userId : Int,
    val title : String,

    @SerializedName("body")
    val subTitle : String
) {
}