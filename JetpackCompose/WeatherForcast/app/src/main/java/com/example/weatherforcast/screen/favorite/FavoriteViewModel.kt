package com.example.weatherforcast.screen.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository)
    : ViewModel(){

    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorite().distinctUntilChanged()
                .collect{ favList ->

                    if(favList.isNullOrEmpty()){
                        Log.d("@@@@" , "In FavoriteViewModel favList is Empty")
                    }
                    else{
                        _favoriteList.value = favList
                        Log.d("@@@@", "In FavoriteViewModel ${_favoriteList.value}")
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite)
    = viewModelScope.launch { repository.insertFavorite(favorite) }

    fun updateFavorite(favorite: Favorite)
        = viewModelScope.launch { repository.updateFavorite(favorite = favorite) }

    fun getFavoriteById(city : String)
        = viewModelScope.launch { repository.getFavoritebyId(city = city) }

    fun delete(favorite: Favorite)
        = viewModelScope.launch { repository.deleteFavorite(favorite = favorite) }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

}