package com.example.jetreader.screens.details

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetreader.components.RoundedButton
import com.example.jetreader.components.topAppBar
import com.example.jetreader.data.Resource
import com.example.jetreader.model.Item
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Preview
@Composable
fun DetailsScreen(navController: NavController = NavController(LocalContext.current),
        BookId : String = "",
        viewModel: DetailViewModel = hiltViewModel()) {
  Scaffold(topBar = {
      topAppBar(title = "Book Details",
          navController = navController,
          showProfile = false,
          icon = Icons.Rounded.ArrowBackIos,
          onBackArrowClicked = {
              navController.popBackStack()
          }
      )
  }) {

      val BookDetail = produceState<Resource<Item>>(initialValue = Resource.Loading()){
          value = viewModel.getBookById(bookId = BookId)
      }.value

      Surface(modifier = Modifier.padding(3.dp)) {

          if(BookDetail.data == null){
              CircularProgressIndicator(

                  color = Color.Red.copy(0.5f)
              )
          }
          else{
              BookDetails(BookDetail = BookDetail,navController)
          }

      }
  }
}


@Composable
fun BookDetails(BookDetail: Resource<Item>, navController : NavController){
  Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(1.dp)
            .fillMaxWidth()) {



    Card( modifier = Modifier.padding(25.dp),
            elevation = 4.dp,
            shape = CircleShape) {

        var imageUrl = BookDetail.data?.volumeInfo?.imageLinks?.thumbnail
        Image(painter = rememberImagePainter(data = imageUrl),
            contentDescription = "Book Thumbnail",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(1.dp)
        )
    }

      Spacer(modifier = Modifier.height(10.dp))
      Text(text = BookDetail.data?.volumeInfo!!.title,
            style = MaterialTheme.typography.h6
      )
      Spacer(modifier = Modifier.height(10.dp))
      Text(text = "Authors: ${BookDetail.data.volumeInfo.authors}",
          style = TextStyle(fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = Color.Gray
          )
      )

      Text(text ="Publish On: ${BookDetail.data.volumeInfo.publishedDate}",
          style = TextStyle(fontStyle = FontStyle.Italic,
              fontSize = 16.sp,
              color = Color.Gray
          )
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(text ="Categories:${BookDetail.data.volumeInfo.categories}",
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
          style = TextStyle(fontStyle = FontStyle.Italic,
              fontSize = 16.sp,
              color = Color.Gray
          )
      )
      Spacer(modifier = Modifier.height(10.dp))

      Text(text ="Page Count: ${BookDetail.data.volumeInfo.pageCount}",
          style = TextStyle(fontStyle = FontStyle.Italic,
              fontSize = 16.sp,
              color = Color.Gray
          )
      )

      Spacer(modifier = Modifier.height(10.dp))

      val localDims = LocalContext.current.resources.displayMetrics

      Surface(modifier = Modifier
          .fillMaxWidth()
          .height(localDims.heightPixels.dp * 0.09f)
          .padding(4.dp)
          .border(border = BorderStroke(2.dp, Color.LightGray))
          .verticalScroll(rememberScrollState())) {
          val cleanDescription = HtmlCompat.fromHtml(BookDetail.data.volumeInfo.description,
              HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
          Text(text =cleanDescription,
              overflow = TextOverflow.Ellipsis,
              style = TextStyle(fontStyle = FontStyle.Italic,
                  fontSize = 16.sp,
                  color = Color.Gray,
                  textAlign = TextAlign.Justify

              ),
          modifier = Modifier.padding(3.dp))
      }
      Spacer(modifier = Modifier.height(10.dp))
      Row(modifier = Modifier
          .padding(1.dp)
          .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
          RoundedButton(label = "Save"){
              val book = MBook(
                    Uid = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                    bookID = BookDetail.data.id,
                    title = BookDetail.data.volumeInfo.title,
                    Author = BookDetail.data.volumeInfo.authors,
                    notes = "",
                    category = BookDetail.data.volumeInfo.categories,
                    images = BookDetail.data.volumeInfo.imageLinks,
                    description = BookDetail.data.volumeInfo.description,
                    pageCount = BookDetail.data.volumeInfo.pageCount,
                    averageRating = 0.0f,
                    ratingsCount = 5,
                    subtitle = BookDetail.data.volumeInfo.subtitle,
                    publishedDate = BookDetail.data.volumeInfo.publishedDate
              )
              SaveTofireStore(book, navController)
          }
          RoundedButton(label = "Cancel"){
             navController.popBackStack()
          }
      }

  }
}

fun SaveTofireStore(book: MBook, navController: NavController) {
    val DataBase = FirebaseFirestore.getInstance()
    val dbCollection = DataBase.collection("books")

    if(book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("docId" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            navController.navigate(AppScreens.HomeScreen.name)
                        }
                    }
                    .addOnFailureListener { task ->
                        Log.d("Exception", "Save to Firebase ${task.localizedMessage}")
                    }
            }
    }
}

@Preview
@Composable
fun Buttons(title : String = "Button",
        onTap: () -> Unit = {}){
    Button(onClick = { onTap.invoke()},
        modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(topEnd = 15.dp, bottomStart = 15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Cyan,
            contentColor = White
        )
        ) {
        Text(text = title,
            color = White)
    }
}











