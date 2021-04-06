package com.yeonkyu.watchaassignment.data.room_persistence

import android.content.Context
import androidx.room.Room

class DatabaseWithRoom(private val applicationContext: Context) {
    companion object{
        private const val DB_NAME = "favorites_db"
    }

    fun invoke(): FavoritesDatabase{
        return Room.databaseBuilder(applicationContext,
        FavoritesDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}