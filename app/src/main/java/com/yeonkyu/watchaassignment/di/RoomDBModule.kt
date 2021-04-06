package com.yeonkyu.watchaassignment.di

import android.app.Application
import androidx.room.Room
import com.yeonkyu.watchaassignment.data.room_persistence.DatabaseWithRoom
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomDBModule = module {

    fun provideRoomDatabase(application: Application) : FavoritesDatabase =
        Room.databaseBuilder(application, FavoritesDatabase::class.java,"favorites_db")
            .build()

    fun provideFavoritesDao(database: FavoritesDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    //single { DatabaseWithRoom(androidApplication().applicationContext).invoke() }
    //single { provideRoomDatabase(androidApplication()) }
    //single { provideFavoritesDao(get()) }

}