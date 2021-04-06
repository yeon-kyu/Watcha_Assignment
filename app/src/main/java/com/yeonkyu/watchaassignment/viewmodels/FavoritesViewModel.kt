package com.yeonkyu.watchaassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.entities.TrackResult
import com.yeonkyu.watchaassignment.data.room_persistence.Favorites
import com.yeonkyu.watchaassignment.data.room_persistence.FavoritesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val mRoomDB: FavoritesDao) : ViewModel() {

    val liveFavoriteTrackList: MutableLiveData<List<Favorites>> by lazy{
        MutableLiveData<List<Favorites>>()
    }

    fun getFavoriteTrackList(){
        CoroutineScope(Dispatchers.Default).launch {
            val favoritesList : List<Favorites> = mRoomDB.getAll()
            liveFavoriteTrackList.postValue(favoritesList)
        }

    }
}