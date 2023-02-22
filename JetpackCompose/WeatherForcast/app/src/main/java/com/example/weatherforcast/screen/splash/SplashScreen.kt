package com.example.weatherforcast.screen.splash


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforcast.navigation.WeatherScreens
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }

    val defultCity = "jaipur"
    
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(0.9f, animationSpec = tween(800,
            easing = {
                OvershootInterpolator(8f)
                    .getInterpolation(it)
            }))
        delay(2000L)
        navController.navigate(WeatherScreens.MainScreen.name+"/$defultCity")
    })

        Surface(modifier = Modifier
            .size(330.dp)
            .padding(15.dp)
            .background(color = Color.White)
            .scale(scale.value),
            shape = CircleShape,
            border = BorderStroke(2.dp,
                color = Color.LightGray),
        ) {

            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(1.dp)) {
            
                Image(painter = painterResource(
                    id = com.example.weatherforcast.R.drawable.sun),
                    contentDescription = "Sun Rise Image",
                    modifier = Modifier.size(95.dp),
                    contentScale = ContentScale.Fit)
                
                Text(text = "Find The Sun?",
                    style = MaterialTheme.typography.h5,
                    color = Color.LightGray)
                

            }

    }



}


