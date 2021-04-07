package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.listeners.TrackListListener
import com.yeonkyu.watchaassignment.data.repository.TrackRepository
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackViewModel(private val repository: TrackRepository, private val roomDB: FavoritesDao) : ViewModel() {

    private var trackListListener: TrackListListener? = null

    private val pagingLimit : Int = 20
    private var pagingOffset : Int = 0

    private val trackList = ArrayList<TrackResult>()
    val liveTrackList: MutableLiveData<ArrayList<TrackResult>> by lazy{
        MutableLiveData<ArrayList<TrackResult>>()
    }
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            postValue(false)
        }
    }
    var isSearching = false

    fun setTrackListener(listener:TrackListListener){
        this.trackListListener = listener
    }

    fun resetTrackList(){
        pagingOffset = 0
        trackList.clear()
    }

    fun searchNextTrack(){
        if(isSearching){
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            isSearching = true

            val favoriteTrackIds : List<Int> = roomDB.getAllId()

            val term = "greenday"
            val entity = "song"
            try {
                val response = repository.searchTrack(term, entity, pagingLimit, pagingOffset)
                Log.e("CHECK_TAG","response size : ${response.resultCount}")

                for(track in response.results){
                    var isFavorite = false
                    for(favoriteTrackId in favoriteTrackIds){//현재 track이 favorite이면 노란 별 체크
                        if(track.trackId==favoriteTrackId){
                            isFavorite = true
                            break;
                        }
                    }
                    track.isFavorite = isFavorite
                    trackList.add(track)
                }
                liveTrackList.postValue(trackList)
                pagingOffset += pagingLimit

            } catch (e: Exception) {
                Log.e("ERROR_TAG", "searchTrack error : $e")
                trackListListener?.makeToast("통신에 문제가 발생하였습니다.")
            }
            finally {
                isLoading.postValue(false)
                isSearching = false
            }
        }
    }

    fun deleteFavorite(favorite: Favorites){
        CoroutineScope(Dispatchers.Default).launch {
            roomDB.delete(favorite)
            Log.e("CHECK_TAG", "start clicked : ${favorite.trackName} 삭제")
        }
    }

    fun insertFavorite(favorite: Favorites){
        CoroutineScope(Dispatchers.Default).launch {
            roomDB.insertTrack(favorite)
            Log.e("CHECK_TAG", "start clicked : ${favorite.trackName} 추가")
        }
    }

}