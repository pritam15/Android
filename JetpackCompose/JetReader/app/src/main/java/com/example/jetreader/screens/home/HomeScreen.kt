package com.example.jetreader.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetreader.components.*
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

//@Preview
@Composable
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = { topAppBar(title = "A. Reader", navController = navController ) },
        floatingActionButton = {
            FABContent{
                navController.navigate(AppScreens.SearchScreen.name)
            }
        }) {
            Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController =  navController,
                viewModel =  viewModel)
        }
    }
}



@Composable
fun HomeContent(navController: NavController,
                viewModel: HomeScreenViewModel){

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUser = if(!email.isNullOrEmpty())
        email.split("@").get(0) else "N/A"
    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Currently Reading Book...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(AppScreens.StatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)

                Text(text = currentUser!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()   
            }
        }

        var listOfBooks = emptyList<MBook>()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if(!viewModel.data.value.data.isNullOrEmpty()){
            if (currentUser != null) {
                listOfBooks = viewModel.data.value?.data!!.toList().filter { mbook ->
                    mbook.Uid == currentUser.uid
                }
            }
        }

        CurrentReadingArea(books = listOfBooks, navController = navController)
    }

}
