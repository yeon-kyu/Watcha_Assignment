package com.yeonkyu.watchaassignment.data.room_persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "trackName") val trackName: String,
    @ColumnInfo(name = "collectionName") val collectionName: String,
    @ColumnInfo(name = "artistName") val artistName: String,
    @ColumnInfo(name = "imgUrl") val imgUrl: String
)