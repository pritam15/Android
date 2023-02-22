package com.example.weatherforcast.screen.setting

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SettingScreen(navController: NavHostController) {
    Surface() {
        Text(text = "Setting")
    }
}