package com.yeonkyu.watchaassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    val isSplashing: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    
    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            isSplashing.postValue(true)
        }
    }

}