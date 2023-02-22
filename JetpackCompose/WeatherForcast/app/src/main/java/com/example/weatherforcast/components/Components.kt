package com.example.weatherforcast.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforcast.model.Current
import com.example.weatherforcast.screen.getWeatherImage


@Composable
fun InfoBox(
    icon : Int,
    value: String,
    desc : String,
    modifier: Modifier = Modifier,

){
        Column(
            modifier = modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

           Icon(painterResource(id = icon),
                contentDescription = "Icon",
                Modifier.size(25.dp))
            
            Text(
                text = value,

                style = TextStyle(fontSize = 14.sp,
                        fontWeight = FontWeight.Bold)
                )

            Text(text = desc,
                style = MaterialTheme.typography.caption,
                color = Color.LightGray)
            
        }

}