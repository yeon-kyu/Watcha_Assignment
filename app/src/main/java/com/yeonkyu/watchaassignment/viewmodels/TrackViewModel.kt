package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.listeners.TrackListListener
import com.yeonkyu.watchaassignment.data.repository.TrackRepository
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
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

    fun setTrackListener(listener:TrackListListener){
        this.trackListListener = listener
    }

    //trackList에 대한 데이터를 초기화 시키는 메소드
    fun resetTrackList(){
        pagingOffset = 0
        trackList.clear()
    }

    //현재 offset으로부터 limit개의 track list를 가져오는 api호출 메소드
    fun searchNextTrack(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)

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
                            break
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
            }
        }
    }

    // DB에서 해당 track을 favorites에서 삭제
    fun deleteFavorite(favorite: Favorites){
        viewModelScope.launch(Dispatchers.Default) {
            roomDB.delete(favorite)
            Log.e("CHECK_TAG", "star clicked : ${favorite.trackName} 삭제")
        }
    }

    //DB에서 해당 track을 favorites에 추가
    fun insertFavorite(favorite: Favorites){
        viewModelScope.launch(Dispatchers.Default) {
            roomDB.insertTrack(favorite)
            Log.e("CHECK_TAG", "star clicked : ${favorite.trackName} 추가")
        }
    }

}