package com.example.jetreader.screens.update

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetreader.R
import com.example.jetreader.components.InputField
import com.example.jetreader.components.RatingBar
import com.example.jetreader.components.RoundedButton
import com.example.jetreader.components.topAppBar
import com.example.jetreader.data.DataOrException
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.AppScreens
import com.example.jetreader.screens.home.HomeScreenViewModel
import com.example.jetreader.utils.FormateDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateScreen(
    navController: NavController,
    bookId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
){

    Scaffold(topBar = {
        topAppBar(title = "Update",
            navController = navController,
            showProfile = false,
            icon = Icons.Rounded.ArrowBackIosNew,
            onBackArrowClicked = {navController.popBackStack()})
    }) {

        Surface(modifier = Modifier) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              val books = produceState<DataOrException<List<MBook>,
                      Boolean,
                      Exception>>(
                  initialValue = DataOrException(data = emptyList(), true, Exception(""))){
                          value = viewModel.data.value
              }.value

                if(books.loading == true){
                    CircularProgressIndicator(
                        color = Color.Red.copy(0.5f),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    books.loading = false
                }
                else{
                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp) {
                        showBookUpdate(bookInfo = viewModel.data.value, bookId, navController )
                    }
                    ShowSimpleForm(book = viewModel.data.value.data?.first { mBook ->
                        mBook.bookID  == bookId
                    }!!, navController = navController )
                }
            }
        }
    }
}
@Composable
fun showBookUpdate(bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
            bookId: String, navController: NavController) {

    Row() {
        Spacer(modifier = Modifier.width(43.dp))
        Column(modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Center) {
            CardListItem(book = bookInfo.data?.first{ mbook ->
                mbook.bookID == bookId },
                onPressDetails = {})
        }
    }


}

@Composable
fun ShowSimpleForm(book: MBook, navController: NavController) {

    val noteText = remember {
        mutableStateOf("")
    }

    val isStartReading = remember{
        mutableStateOf(false)
    }
    val isFinishReading = remember{
        mutableStateOf(false)
    }

    val ratingValue = remember{
        mutableStateOf(0)
    }

    SimpleForm(defaultValue = if(book.notes.toString().isNotEmpty()) book.notes.toString()
                else "No thoughts available"){ notes ->
         noteText.value = notes
    }
    
    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {

        if(book.startReading == null){
            TextButton(onClick = { isStartReading.value = true },
                enabled = book.startReading == null) {
                if(!isStartReading.value){
                    Text(text = "Start Reading")
                }
                else{
                    Text(text = "Started Reading on!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(0.5f))
                }
            }
        }
        else{
            Text(text = "Start on: ${FormateDate(book.startReading!!)}") // Todo format data
        }

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(onClick = { isFinishReading.value = true },
                enabled = book.endReading == null) {
            if(book.endReading == null){
                if(!isFinishReading.value){
                    Text(text = "Mark as Read")
                }
                else{
                    Text(text = "Finished Reading!")
                }
            }
            else{
                Text(text = "Finished on: ${FormateDate(book.endReading!!)}") // Todo: formate
            }
        }
    }
    
    Text(text = "Rating",
            modifier = Modifier.padding(bottom = 3.dp))
    book.ratingsCount?.toInt().let {
        RatingBar(rating = it!!){ rating ->
            ratingValue.value = rating
        }
    }
    
    Spacer(modifier = Modifier.padding(15.dp))
    Row{
        val context = LocalContext.current
        val changedNotes = book.notes != noteText.value
        val changedRating = book.ratingsCount != ratingValue.value
        val finishedTimeStamp = if(isFinishReading.value) Timestamp.now()
        else book.endReading
        val startedReadingTimeStamp = if(isStartReading.value) Timestamp.now()
        else book.startReading

        val bookUpdate = changedNotes || changedRating ||
                isStartReading.value || isFinishReading.value

        val bookToUpdate = hashMapOf(
            "end_reading" to finishedTimeStamp,
            "start_reading" to startedReadingTimeStamp,
            "ratings_count" to ratingValue.value,
            "notes" to noteText.value
        ).toMap()

        RoundedButton(label = "Update"){
            if(bookUpdate){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.docId!!)
                    .update(bookToUpdate)
                    .addOnSuccessListener {
                        Log.d("Update Book", "ShowSimpleForm: Book Updated : $it")
                        ShowToast(context = context, Msg = "Book Updated Sucessfully")
                        navController.navigate(AppScreens.HomeScreen.name)
                    }
                    .addOnFailureListener{
                        Log.d("Update Book", "ShowSimpleForm: Exception : ${it.localizedMessage}")
                    }
            }
        }
        Spacer(modifier = Modifier.width(100.dp))

        val openDialog = remember {
            mutableStateOf(false)
        }

        if(openDialog.value){
            ShowAlertDialog(message = stringResource(id = R.string.sure) +"\n"+
                        stringResource(id = R.string.action),
                    openDialog = openDialog){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.docId!!)
                    .delete()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            openDialog.value = false
                            navController.navigate(AppScreens.HomeScreen.name)
                        }
                    }
            }
        }
        RoundedButton(label = "Delete"){
            openDialog.value = true
        }
    }
}

@Composable
fun ShowAlertDialog(message: String,
    openDialog : MutableState<Boolean>,
    onYesPressed : () -> Unit) {

    if(openDialog.value){
        AlertDialog(onDismissRequest = { /*TODO*/ },
                title = { Text(text = "Delete Book")},
                text = { Text(text = message)},
                buttons = {
                    Row(modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center) {

                        TextButton(onClick = { onYesPressed.invoke() }) {
                            Text(text = "Yes")
                        }
                        
                        TextButton(onClick = { openDialog.value = false }) {
                            Text(text = "Dismiss")
                        }
                    }
                })


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading : Boolean = false,
    defaultValue : String = "",
    onSearch:(String) -> Unit){

    Column() {
        val textFieldInput = rememberSaveable{
            mutableStateOf(defaultValue)
        }
        val keyBoardController = LocalSoftwareKeyboardController.current
        val valid =  remember (textFieldInput.value.trim()){textFieldInput.value.trim().isNotEmpty()}

        InputField(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = textFieldInput,
            labelId = "Enter Your Notes" ,
            enabled = true,
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                keyBoardController?.hide()
            })
    }
}

@Composable
fun CardListItem(book: MBook?,
                 onPressDetails: () -> Unit) {

    Card(modifier = Modifier
        .padding(
            start = 4.dp, end = 4.dp,
            top = 4.dp, bottom = 8.dp
        )
        .clip(RoundedCornerShape(20.dp))
        .clickable { },
        elevation = 8.dp){

        Row(horizontalArrangement = Arrangement.Start) {
            if (book != null) {
                Image(painter = rememberImagePainter(data = book.images?.thumbnail),
                    contentDescription ="Book thumbnail",
                    modifier = Modifier
                        .height(100.dp)
                        .width(120.dp)
                        .padding(4.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 120.dp, topEnd = 120.dp,
                                bottomStart = 0.dp, bottomEnd = 0.dp
                            )
                        ))

                Column {
                    Text(text = book.title.toString(),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(120.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(text = book.Author.toString(),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                            .width(120.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(text = book.publishedDate.toString(),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                            .width(120.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }

    }
}


fun ShowToast(context : Context, Msg : String){
    Toast.makeText(context,Msg,Toast.LENGTH_SHORT)
}




