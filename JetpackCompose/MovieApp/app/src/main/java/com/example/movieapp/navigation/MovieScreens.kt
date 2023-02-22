package com.example.movieapp.navigation

enum class MovieScreens {

    MovieHome,
    DetailScreen;


    companion object {
        fun fromRoute(route : String?) : MovieScreens
        = when(route?.substringBefore("/")){
               MovieHome.name -> MovieHome
                DetailScreen.name -> DetailScreen
                null -> MovieHome
                else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}


