package com.yeonkyu.watchaassignment.data.room_persistence

import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(vararg track: Favorites)

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorites>

    @Delete
    fun delete(track: Favorites)
}