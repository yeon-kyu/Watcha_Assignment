package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun getFavoriteTrackList(){
        CoroutineScope(Dispatchers.Default).launch {
            val favoritesList : List<Favorites> = roomDB.getAll()
            liveFavoriteTrackList.postValue(favoritesList)
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