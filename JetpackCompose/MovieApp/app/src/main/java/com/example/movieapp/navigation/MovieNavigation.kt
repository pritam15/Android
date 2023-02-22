package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.Screens.Detail.Detail.DetailScreen
import com.example.movieapp.Screens.Home.MovieHome

@Composable
fun MovieNavigation(){

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MovieScreens.MovieHome.name){

        composable(MovieScreens.MovieHome.name){

                MovieHome(navController = navController)
        }
        
        composable(MovieScreens.DetailScreen.name+"/{movie}",
            arguments = listOf(navArgument(name = "movie") {type = NavType.StringType})){ backStackEntry ->
            DetailScreen(navController = navController,
                backStackEntry.arguments?.getString("movie")
            )
        }
    }


}