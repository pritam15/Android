package com.example.weatherforcast.screen.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.widgets.WeatherAppBar
import kotlinx.coroutines.flow.collect

@SuppressLint("SuspiciousIndentation")
@Composable
fun FavoriteScreen(navController: NavHostController) {
   val favoriteViewModel : FavoriteViewModel = hiltViewModel()
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
            title = "Favorite",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack){
            navController.popBackStack()
        }
    }) {

        Surface(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()) {
            
            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                val list = favoriteViewModel.favoriteList.collectAsState().value
                    LazyColumn{
                        items(items = list){ item ->
                            FavRow(favorite = item,
                                navController = navController,
                                favoriteViewModel = favoriteViewModel)
                        }
                }

            }

        }

    }
}

@Composable
fun FavRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .height(50.dp)
        .clickable {
            navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
        },
        color = Color(0xFFB2DFDB),
        shape = CircleShape.copy(topEnd = CornerSize(0.dp)),
    ){

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            Text(text = favorite.city,
                Modifier.padding(start = 4.dp),
                fontSize = 17.sp)

            Surface(shape = CircleShape,
                modifier = Modifier.padding(0.dp),
                color = Color(0xFFD1E3E1)) {

                Text(text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption,
                    fontSize = 16.sp)
            }

            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable {
                    favoriteViewModel.delete(favorite = Favorite(favorite.city,favorite.country))
                })
        }
    }
}