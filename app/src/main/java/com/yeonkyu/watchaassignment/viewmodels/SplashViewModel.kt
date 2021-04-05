package com.yeonkyu.watchaassignment.viewmodels

import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.listeners.SplashListener

class SplashViewModel : ViewModel() {

    private var splashListener: SplashListener? = null

    fun setSplashListener(listener: SplashListener){
        splashListener = listener
    }

}