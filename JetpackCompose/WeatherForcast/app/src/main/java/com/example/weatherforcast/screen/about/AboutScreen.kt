package com.example.weatherforcast.screen.about

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AboutScreen(navController: NavHostController) {
    Surface() {
        Text(text = "About")
    }
}