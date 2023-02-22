package com.example.jetreader.navigation

enum class AppScreens {
    DetailsScreen,
    HomeScreen,
    LoginScreen,
    SignUpScreen,
    SearchScreen,
    SplashScreen,
    StatsScreen,
    UpdateScreen;

    companion object {
        fun fromRoute(route: String) : AppScreens =
            when(route?.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                SignUpScreen.name -> SignUpScreen
                SearchScreen.name -> SearchScreen
                DetailsScreen.name -> DetailsScreen
                UpdateScreen.name -> UpdateScreen
                StatsScreen.name -> StatsScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}