package com.example.weatherforcast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "fav_table")
data class Favorite(
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city : String,

    @ColumnInfo(name = "country")
    val country : String
)
