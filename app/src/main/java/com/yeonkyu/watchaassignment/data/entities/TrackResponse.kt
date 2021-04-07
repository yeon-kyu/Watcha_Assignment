package com.yeonkyu.watchaassignment.data.entities

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName(value = "resultCount")
    val resultCount: Int,
    @SerializedName(value = "results")
    val results: ArrayList<TrackResult>
)
