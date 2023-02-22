package com.example.weatherforcast.di;

import android.content.Context
import androidx.room.Room
import com.example.weatherforcast.data.WeatherDatabase
import com.example.weatherforcast.network.WeatherAPI
import com.example.weatherforcast.util.Constant
import dagger.Module;
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun provideOpenWeatherApi() : WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideFavoriteDao(weatherDatabase: WeatherDatabase)
        = weatherDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context : Context): WeatherDatabase
        = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration()
        .build()

}











