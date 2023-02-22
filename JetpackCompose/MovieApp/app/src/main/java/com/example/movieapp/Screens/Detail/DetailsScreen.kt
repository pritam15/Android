package com.example.movieapp.Screens.Detail.Detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.Screens.Home.MainContent
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies
import com.example.movieapp.widget.MovieCard

@Composable
fun DetailScreen(navController: NavController, movieId: String?) {

    val newMovieList = getMovies().filter { movie ->
         movie.id == movieId
    }
    Scaffold(topBar = { // topBar Trailing Lambda function
        TopAppBar(backgroundColor = Color.Magenta) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back",
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        })

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Movie", modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }
    }){
        // Scaffold Body


        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            Column(modifier = Modifier.fillMaxWidth()) {

                MovieCard(newMovieList[0])

                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.padding(8.dp), color = Color.DarkGray)

                ImageRow(newMovieList)
            }

        }

    }
}

@Composable
private fun ImageRow(newMovieList: List<Movie>) {
    LazyRow() {

        items(items = newMovieList[0].images) { image ->
            Card(
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .padding(8.dp),
                elevation = 6.dp
            ) {

                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = "Movie Images",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}



