package com.example.jetreader.components

import android.util.Log
import android.view.MotionEvent
import android.widget.RatingBar
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetreader.R
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.AppScreens
import com.example.jetreader.screens.home.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderLogo(modifier: Modifier = Modifier){
    Text(text = "A. Reader",
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(0.5f),
        modifier = modifier.padding(bottom = 16.dp))
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction)


}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
            imeAction = imeAction),
        keyboardActions = onAction)


}




@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()
    OutlinedTextField(value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction)

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible}) {
        Icons.Default.Close

    }

}




@Composable
fun topAppBar(title : String,
              showProfile : Boolean = true,
              navController: NavController,
              icon : ImageVector? = null,
              onBackArrowClicked : () -> Unit = {}
){

    TopAppBar(
        title = {
            if(showProfile){
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "Logo Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f))
            }
            if(icon != null){
                Icon(imageVector = icon,
                    contentDescription = "Back Arrow",
                    tint = Color.Red.copy(0.5f),
                    modifier = Modifier.clickable { onBackArrowClicked() })
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = title,
                color = Color.Red.copy(0.7f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
        },
        actions = {
            IconButton(onClick = { FirebaseAuth.getInstance().signOut().run {
                navController.navigate(AppScreens.LoginScreen.name)
            } }) {

                if(showProfile) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "LogOut",
                        tint = Color.Green.copy(0.4f)
                    )
                }

            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )

}


@Composable
fun CurrentReadingArea(books:List<MBook>,
                       navController: NavController
){

    ReadingRightNowArea(listOfBooks = books, navController = navController)

    TitleSection(label = "your Books..")

    BookListArea(books,navController)

}


@Composable
fun ReadingRightNowArea(listOfBooks: List<MBook>,
                        navController: NavController) {

    val readingNowList = listOfBooks.filter { mbook ->
        mbook.startReading != null && mbook.endReading == null
    }

    HorizontalScrollableComponent(readingNowList) {

    }
}

@Composable
fun BookListArea(books: List<MBook>, navController: NavController) {

    val addedBooks = books.filter{ mbook ->
        mbook.startReading == null && mbook.endReading == null
    }
    HorizontalScrollableComponent(books = addedBooks, onCardPressed = {
        navController.navigate(AppScreens.UpdateScreen.name + "/${it}")
    })

}

@Composable
fun HorizontalScrollableComponent(books: List<MBook>,
                                  viewModel : HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit
                                  ) {

       val horizontalScrollState = rememberScrollState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)
        .horizontalScroll(horizontalScrollState)) {


        if(viewModel.data.value.loading == true){
            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(
                    color = Color.Red.copy(alpha = 0.5f)
                )
            }
        }
        else{
            if(books.isNullOrEmpty()){
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found. Add a Book",
                        style = TextStyle(Color.Red.copy(0.5f)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp)
                }
            }
            else{
                for(book in books){
                    ListCard(book){
                        onCardPressed(book.bookID.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun TitleSection( modifier: Modifier = Modifier,
                  label : String){

    Surface(modifier = Modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}







@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color(0xFF92CBDF)
    ) {
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "Add A Book",
            tint = Color.White)
    }

}


@Composable
fun ListCard(book : MBook = MBook(),
             onPressDetails : (String) -> Unit = {}){

    val context = LocalContext.current
    val resources = context.resources

    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(shape = RoundedCornerShape(29.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }) {

        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(painter = rememberImagePainter(data = book.images?.thumbnail),
                    contentDescription = "Book Poster",
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp))

                Spacer(modifier = Modifier.width(50.dp))

                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite Btn",
                        modifier = Modifier.padding(1.dp))

                    RatingBar(score = 3.5)
                }
            }

            Text(text = book.title.toString(),
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)

            Text(text = book.Author.toString(),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption)

            val isStartReading = remember {
                mutableStateOf(false)
            }
            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                isStartReading.value = book.startReading != null
                RoundedButton( label = if(isStartReading.value) "Reading"
                else "Not Start", radius = 70)
            }
        }
    }

}


@Composable
fun RatingBar(score : Double = 4.5){
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        elevation = 6.dp,
        color = Color.White){

        Column(modifier = Modifier.padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Rounded.StarBorder,
                contentDescription = "Rating",
                modifier = Modifier.padding(3.dp))
            Text(text = score.toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}


@Composable
fun RoundedButton(
    label : String = "Reading",
    radius : Int = 29,
    onPress:() -> Unit = {}
){

    Surface(modifier = Modifier.clip(
        RoundedCornerShape(topStartPercent = radius,
        bottomEndPercent = radius)
    ),
        color = Color(0xFF92CBDF)) {
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating : Int,
    onPress: (Int) -> Unit
){

    var ratingState by remember{
        mutableStateOf(rating)
    }

    var select by remember {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if(select) 42.dp
                        else 34.dp,
    spring(Spring.DampingRatioMediumBouncy))
    
    Row(modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        for(i in 1..5){
            Icon(painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
                contentDescription = "Star",
                modifier = Modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                select = true
                                onPress(i)
                                ratingState = i
                            }
                            MotionEvent.ACTION_UP -> {
                                select = false
                            }
                        }
                        true
                    },
            tint = if(i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1))
    }
    }
}




