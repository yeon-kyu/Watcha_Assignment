package com.yeonkyu.watchaassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonkyu.watchaassignment.data.listeners.SplashListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private var splashListener: SplashListener? = null

    val isSplashing: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    //viewModel에서 SplashActivity의 매소드를 호출할 수 있도록 listener 인터페이스를 활용하였습니다.
    fun setSplashListener(listener: SplashListener){
        splashListener = listener
    }

    //splashActivity가 create()되었을때 호출되는 메소드입니다
    fun initiate(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            isSplashing.postValue(true)
        }
    }

}