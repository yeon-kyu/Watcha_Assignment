package com.yeonkyu.watchaassignment.data.entities

import com.google.gson.annotations.SerializedName

data class TrackResult(
    @SerializedName(value = "trackId")
    val trackId: Int,
    @SerializedName(value = "trackName")
    val trackName: String,
    @SerializedName(value = "collectionName")
    val collectionName: String,
    @SerializedName(value = "artistName")
    val artistName: String,
    @SerializedName(value = "artworkUrl60")
    val ImageUrl: String
)
