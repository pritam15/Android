package com.example.weatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforcast.screen.MainScreen
import com.example.weatherforcast.screen.about.AboutScreen
import com.example.weatherforcast.screen.favorite.FavoriteScreen
import com.example.weatherforcast.screen.splash.WeatherSplashScreen
import com.example.weatherforcast.screen.main.ViewModel
import com.example.weatherforcast.screen.search.SearchScreen
import com.example.weatherforcast.screen.setting.SettingScreen

@Composable
fun WeatherNavigation() {
    val NavController = rememberNavController()
    NavHost(navController = NavController,
        startDestination = WeatherScreens.SplashScreen.name){

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = NavController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
                    arguments = listOf(
                        navArgument(name = "city"){
                            type = NavType.StringType
                        }
                    )){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val viewModel = hiltViewModel<ViewModel>()
                MainScreen(navController = NavController,
                    viewModel = viewModel,
                    city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = NavController)
        }


        composable(WeatherScreens.FavoriteScreen.name){
            FavoriteScreen(navController = NavController)
        }


        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = NavController)
        }


        composable(WeatherScreens.SettingScreen.name){
           SettingScreen(navController = NavController)
        }
    }
}
