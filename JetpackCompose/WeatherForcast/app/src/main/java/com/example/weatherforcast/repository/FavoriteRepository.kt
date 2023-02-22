package com.example.weatherforcast.repository

import androidx.room.Dao
import com.example.weatherforcast.data.FavoriteDao
import com.example.weatherforcast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao : FavoriteDao) {

    fun getFavorite() : Flow<List<Favorite>> = dao.getFavorite()

    suspend fun insertFavorite(favorite: Favorite) = dao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = dao.updateFavorite(favorite = favorite)
    suspend fun getFavoritebyId(city : String) : Favorite= dao.getFavById( city= city)
    suspend fun deleteFavorite(favorite: Favorite) = dao.delete(favorite = favorite)
    suspend fun deleteAll() = dao.deleteAllFavorite()

}