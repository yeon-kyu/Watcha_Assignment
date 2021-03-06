package com.yeonkyu.watchaassignment.data.repository

import com.yeonkyu.watchaassignment.data.api.ITunesSearchService
import com.yeonkyu.watchaassignment.data.entities.TrackResponse

class TrackRepository(private val searchService: ITunesSearchService) : BaseRepository() {
    suspend fun searchTrack(term:String, entity:String, limit:Int, offset:Int): TrackResponse =
            apiRequest { searchService.searchTrack(term,entity,limit,offset) }
}