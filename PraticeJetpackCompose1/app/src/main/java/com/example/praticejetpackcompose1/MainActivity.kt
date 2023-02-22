package com.example.praticejetpackcompose1

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praticejetpackcompose1.ui.theme.PraticeJetpackCompose1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PraticeJetpackCompose1Theme {
                // A surface container using the 'background' color from the theme
                TapCounter()
            }
        }
    }
}


@Composable
fun TapCounter(){

    var counter = remember{
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFF97A179)
    ) {
        Column(modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
            
            Text(text = "${counter.value}", modifier = Modifier, style = TextStyle(
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.ExtraBold,
            ))
            Spacer(modifier = Modifier.height(150.dp))
            CreateCircel(counter = counter.value){
                counter.value  = it + 1
            }
        }
    }
}


@Composable
fun CreateCircel(counter : Int = 0, updateCounter:(Int) -> Unit){

  Card(modifier = Modifier
      .size(145.dp)
      .clickable {
            updateCounter(counter)
      },
      elevation = 4.dp,
      shape = CircleShape) {
        Box(modifier = Modifier,
            contentAlignment = Alignment.Center) {
            Text(text = "Tap")
        }
  }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PraticeJetpackCompose1Theme {
        TapCounter()
    }
}