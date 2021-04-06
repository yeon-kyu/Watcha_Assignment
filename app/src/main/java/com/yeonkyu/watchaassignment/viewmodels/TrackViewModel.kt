package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.repository.TrackRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackViewModel(private val repository: TrackRepository) : ViewModel() {

    val pagingLimit : Int = 20
    var currentPage : Int = 0

    private val trackList = ArrayList<TrackResult>()
    val liveTrackList: MutableLiveData<ArrayList<TrackResult>> by lazy{
        MutableLiveData<ArrayList<TrackResult>>()
    }

    fun searchTrack(){
        CoroutineScope(Dispatchers.IO).launch {
            val term = "greenday"
            val entity = "song"
            try {
                val response = repository.searchTrack(term, entity)
                Log.e("CHECK_TAG", response.resultCount.toString())
                Log.e("CHECK_TAG", response.results[0].trackName)

                trackList.addAll(response.results)
                liveTrackList.postValue(trackList)

            } catch (e: Exception) {
                Log.e("ERROR_TAG", "searchTrack error : $e")
            }
        }

    }

}