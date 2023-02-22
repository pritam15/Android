package com.example.weatherforcast.widgets

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.screen.favorite.FavoriteViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    favorite: Favorite? = null,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
   ){

        var showDialog = remember {
            mutableStateOf(false)
        }

    if(showDialog.value){
            showDropDownMenu(showDialog = showDialog, navController = navController)
        }

        TopAppBar(
            title = { Text(text = title,
                            color = MaterialTheme.colors.secondary,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            ),

            )},
            actions = {
                     if(isMainScreen){
                         IconButton(onClick = { onAddActionClicked.invoke() }) {
                             Icon(imageVector = Icons.Default.Search,
                                 contentDescription = "Search")
                         }

                         IconButton(onClick = { showDialog.value = true
                             Log.d("Show Dialog", "Dialog Visible ${showDialog.value}")}){
                             Icon(imageVector = Icons.Rounded.MoreVert,
                                 contentDescription = "More" )
                         }
                     }
                else Box {}
            },
            navigationIcon = {
              if(icon != null && !isMainScreen){
                  Icon(imageVector = icon,
                      contentDescription ="Back",
                      tint = MaterialTheme.colors.onSecondary,
                      modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                      }
                  )
              }

             if(isMainScreen){

                 val isFavorite = favoriteViewModel.favoriteList.collectAsState().value.filter { item ->
                     item.city == favorite?.city
                 }

                 var color: ULong
                 if(isFavorite.isNullOrEmpty()){
                     color = Color.LightGray.copy(0.6f).value
                 }
                 else{
                     color = Color.Red.copy(0.6f).value
                 }

                       Icon(imageVector = Icons.Rounded.Favorite,
                         contentDescription = "Add Favorite",
                         modifier = Modifier
                             .scale(0.9f)
                             .padding(start = 2.dp)
                             .clickable {
                                 Log.d("@@@@", " Before ${isFavorite.size}")

                                 if (isFavorite.isNullOrEmpty()){
                                     favoriteViewModel.insertFavorite(favorite!!)
                                     Log.d("@@@@", "Insert ${isFavorite.size}")
                                     color = Color.Red.copy(0.6f).value
                                 }
                                 else{
                                     favoriteViewModel.delete(favorite!!)
                                     Log.d("@@@@", "Delete ${isFavorite.size}")
                                     color = Color.LightGray.copy(0.6f).value
                                 }

                                 Log.d("@@@@", "After ${isFavorite.size}")

                             },
                         tint = Color(color)
                       )


             }

            },
            backgroundColor = Color.Transparent,
            elevation = elevation
        )

}


@Composable
fun showDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController,
) {
    val items = listOf("Favorite" , "About", "Settings")
    var expanded by remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(align = Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false
                            showDialog.value = false},
                modifier = Modifier
                    .width(140.dp)
            ){
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        showDialog.value = false

                    }){
                        Icon(imageVector = when(item){
                                                     "Favorite" -> Icons.Default.Favorite
                                                    "About" -> Icons.Default.Info
                                                    else -> Icons.Default.Settings
                                                     },
                            contentDescription = null,
                            tint = Color.LightGray)

                        Text(text = " $item",
                            modifier = Modifier.clickable {
                              navController.navigate(
                                  when(item){
                                      "Favorite" -> WeatherScreens.FavoriteScreen.name
                                      "About" -> WeatherScreens.AboutScreen.name
                                      else -> WeatherScreens.SettingScreen.name
                                  }
                              )

                            },
                            fontWeight = W300,
                        )
                    }
                }
            }
    }

}






