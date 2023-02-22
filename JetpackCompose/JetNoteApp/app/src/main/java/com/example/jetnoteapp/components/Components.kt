package com.example.jetnoteapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetnoteapp.model.Notes
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    lable : String,
    maxLine : Int = 1,
    onTextChange : (String) -> Unit ,
    onImeAction: () -> Unit = {},
    ){
        val keyBoardController = LocalSoftwareKeyboardController.current
        TextField(value = text,
            onValueChange = onTextChange,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent),
            maxLines = maxLine,
            label = { Text(text = lable)},
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyBoardController?.hide()
            }),
            modifier = modifier
        )
    }

@Composable
fun SaveButton(text: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    enabled : Boolean = true){

    Button(onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier) {
                Text(text = text)
    }

}

@Composable
fun NoteCard(note : Notes,
            modifier: Modifier = Modifier,
            onNoteClicked: (Notes) -> Unit){
    Card(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(topStart = 33.dp, bottomEnd = 33.dp)),
        elevation = 6.dp,
        backgroundColor = Color.LightGray) {


        Column(modifier = modifier
            .fillMaxWidth()
            .padding(13.dp)
            .clickable { onNoteClicked(note) }) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)){
                    append(note .title)
                }

            })


            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                ) {
                    append(note.description)
                }
            })

            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                ) {
                    val localDate : Date? = note.saveDate
                    val formatter5 = SimpleDateFormat("dd-MM-YYYY hh:mm")
                    val formats1: String = formatter5.format(localDate)
                    append(formats1.toString())
                }
            }, modifier = Modifier.padding(top = 10.dp))
        }




    }
}