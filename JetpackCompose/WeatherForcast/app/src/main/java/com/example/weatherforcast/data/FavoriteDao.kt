package com.example.weatherforcast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforcast.model.Favorite
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete as Delete

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM fav_table")
   fun getFavorite() : Flow<List<Favorite>>

   @Query("SELECT * FROM fav_table WHERE city =:city")
   suspend fun getFavById(city : String) : Favorite

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertFavorite(favorite : Favorite)

   @Update(onConflict = OnConflictStrategy.REPLACE)
   suspend fun updateFavorite(favorite : Favorite)

   @Query("DELETE FROM fav_table")
   suspend fun deleteAllFavorite()

   @Delete
   suspend fun delete(favorite: Favorite)
}