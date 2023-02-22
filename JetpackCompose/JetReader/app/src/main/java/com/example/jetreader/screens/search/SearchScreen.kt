package com.example.jetreader.screens.search.SearchScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetreader.components.InputField
import com.example.jetreader.components.topAppBar
import com.example.jetreader.model.Item
import com.example.jetreader.navigation.AppScreens
import com.example.jetreader.screens.search.SearchViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()){


    Scaffold(topBar = {
       topAppBar(title = "Search Books",
           icon = Icons.Default.ArrowBack,
           navController = navController,
            showProfile = false){
           navController.navigate(AppScreens.HomeScreen.name)}
    }) {

        Surface() {
            Column {
                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                viewModel = searchViewModel){ query ->
                    searchViewModel.SearchBook(query)
                }

                Spacer(modifier = Modifier.height(13.dp))

                BookList(navController, searchViewModel)
            }
        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loding : Boolean = false,
    hint : String = "Search",
    viewModel: SearchViewModel,
    onSearch : (String) -> Unit = {}
){

    val searchQuery = rememberSaveable { mutableStateOf("") }
    val valid = remember(searchQuery.value) { searchQuery.value.trim().isNotEmpty() }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    InputField(
        valueState = searchQuery,
        labelId = "Search",
        enabled = true,
        onAction = KeyboardActions{
            if(!valid)return@KeyboardActions

            onSearch.invoke(searchQuery.value.trim())
            searchQuery.value = ""
            keyboardController?.hide()
        }
    )


    }

@Composable
private fun BookList(navController : NavController,
                     viewModel: SearchViewModel) {

    var itemList = viewModel.listOfItem

    if(viewModel.isLoading){
         LinearProgressIndicator(
             modifier = Modifier.fillMaxWidth(),
             backgroundColor = Color.LightGray,
             color = Color.Red.copy(0.5f),
         )
    }
    else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(itemList) { item ->
                BookCard(item){
                    navController.navigate(AppScreens.DetailsScreen.name + "/${item.id}")
                }
            }
        }
    }
}



@Composable
fun BookCard(item: Item, onClick: () -> Unit = {}){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .height(100.dp)
        .clip(RectangleShape)
        .clickable { onClick.invoke() },
        elevation = 7.dp){

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)) {
            val imageUrl : String = item.volumeInfo.imageLinks.smallThumbnail
            Log.d("ImageUrl", "BookCard: $imageUrl")
            Image(painter = rememberImagePainter(data = imageUrl),
                contentDescription ="Book Poster",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .padding(1.dp))
            
            Column {
                Text(text = item.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis)

                Text(text = "Authors ${item.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style =  MaterialTheme.typography.caption
                )


                Text(text = "Published on ${ item.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style =  MaterialTheme.typography.caption
                )


                Text(text = "Categories ${ item.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style =  MaterialTheme.typography.caption
                )
            }

        }
    }
}