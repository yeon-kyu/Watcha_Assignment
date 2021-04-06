package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.listeners.TrackListListener
import com.yeonkyu.watchaassignment.data.repository.TrackRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackViewModel(private val repository: TrackRepository) : ViewModel() {

    private var trackListListener: TrackListListener? = null

    val pagingLimit : Int = 20
    var currentPage : Int = 0

    private val trackList = ArrayList<TrackResult>()
    val liveTrackList: MutableLiveData<ArrayList<TrackResult>> by lazy{
        MutableLiveData<ArrayList<TrackResult>>()
    }
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            postValue(false)
        }
    }

    fun setTrackListener(listener:TrackListListener){
        this.trackListListener = listener
    }

    fun resetTrackList(){
        trackList.clear()
    }

    fun searchTrack(){
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
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
                trackListListener?.makeToast("통신에 문제가 발생하였습니다.")
            }
            finally {
                isLoading.postValue(false)
            }
        }

    }

}