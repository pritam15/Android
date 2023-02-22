package com.example.movieapp.Screens.Home


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies
import com.example.movieapp.navigation.MovieScreens
import com.example.movieapp.widget.MovieCard


@Composable
fun MovieHome(navController: NavController){


    Scaffold(topBar = { // topBar Trailing Lambda function
        TopAppBar(backgroundColor = Color.Magenta) {
            Text(text = "Movies")
        }
    } ) {
        // Scaffold Body
      MainContent(navController)
    }
}




@Composable
fun MainContent(  navController: NavController,
    moviesList : List<Movie> = getMovies()){
    Surface(color = MaterialTheme.colors.background) {

        LazyColumn{

            items(items = moviesList){
                MovieCard(movie = it) { movie ->
                   navController.navigate(route = MovieScreens.DetailScreen.name+"/${movie.id}")
                }
            }
        }
    }
}



