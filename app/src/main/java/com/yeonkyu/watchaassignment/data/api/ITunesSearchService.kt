package com.yeonkyu.watchaassignment.data.api

import com.yeonkyu.watchaassignment.data.entities.TrackResponse
import retrofit2.Response

interface ITunesSearchService {
    fun searchTrack() : Response<TrackResponse>
}