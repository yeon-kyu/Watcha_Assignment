package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val roomDB: FavoritesDao) : ViewModel() {

    val liveFavoriteTrackList: MutableLiveData<List<Favorites>> by lazy{
        MutableLiveData<List<Favorites>>()
    }

    //DB에서 모든 favorite
    fun getFavoriteTrackList(){
        viewModelScope.launch(Dispatchers.Default) {
            val favoritesList : List<Favorites> = roomDB.getAll()
            liveFavoriteTrackList.postValue(favoritesList)
        }
    }

    // DB에서 해당 track을 favorites에서 삭제
    fun deleteFavorite(favorite: Favorites){
        viewModelScope.launch(Dispatchers.Default) {
            roomDB.delete(favorite)
            Log.e("CHECK_TAG", "start clicked : ${favorite.trackName} 삭제")
        }
    }

    //DB에서 해당 track을 favorites에 추가
    fun insertFavorite(favorite: Favorites){
        viewModelScope.launch(Dispatchers.Default) {
            roomDB.insertTrack(favorite)
            Log.e("CHECK_TAG", "start clicked : ${favorite.trackName} 추가")
        }
    }

}