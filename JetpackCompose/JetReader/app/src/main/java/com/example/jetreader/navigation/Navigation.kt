package com.example.jetreader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetreader.screens.details.DetailViewModel
import com.example.jetreader.screens.details.DetailsScreen
import com.example.jetreader.screens.home.HomeScreen
import com.example.jetreader.screens.home.HomeScreenViewModel
import com.example.jetreader.screens.login.LoginScreen
import com.example.jetreader.screens.login.SignUpScreen
import com.example.jetreader.screens.search.SearchScreen.SearchScreen
import com.example.jetreader.screens.search.SearchViewModel
import com.example.jetreader.screens.splash.SplashScree
import com.example.jetreader.screens.update.UpdateScreen

@Composable
fun Navigation(){
    /* navController is used to direct where we need to go */
    val navController = rememberNavController()
    NavHost(navController = navController,
            startDestination = AppScreens.SplashScreen.name){

        composable(AppScreens.SplashScreen.name){
            SplashScree(navController = navController)
        }

        composable(AppScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        composable(AppScreens.SignUpScreen.name){
            SignUpScreen(navController = navController)
        }

        composable(AppScreens.HomeScreen.name){
            val viewModel = hiltViewModel<HomeScreenViewModel>()
           HomeScreen(navController = navController, viewModel)
        }

        composable(AppScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController,searchViewModel)
        }


        val detailScreenRoute = AppScreens.DetailsScreen.name
        //Passing data to NavArguments
        composable("$detailScreenRoute/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){ backStackEntry ->
            val viewModel = hiltViewModel<DetailViewModel>()
            //geting  data from navArguments
            backStackEntry.arguments?.getString("bookId").let {
                DetailsScreen(navController = navController, BookId = it.toString(),
                    viewModel = viewModel)
            }
        }

        val updateScreen = AppScreens.UpdateScreen.name
        composable("$updateScreen/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){ backStackEntry ->

            val viewModel = hiltViewModel<HomeScreenViewModel>()

            backStackEntry.arguments?.getString("bookId").let{
                UpdateScreen(navController,it.toString(),
                  viewModel)
            }
        }
    }
}
