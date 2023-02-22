package com.example.weatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforcast.data.FavoriteDao
import com.example.weatherforcast.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

   abstract fun favoriteDao() : FavoriteDao
}