package com.yeonkyu.watchaassignment.data.api

import com.yeonkyu.watchaassignment.data.entities.TrackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchService {
    @GET("/search?")
    suspend fun searchTrack(
        @Query("term") term:String,
        @Query("entity") entity:String
    ) : Response<TrackResponse>

}