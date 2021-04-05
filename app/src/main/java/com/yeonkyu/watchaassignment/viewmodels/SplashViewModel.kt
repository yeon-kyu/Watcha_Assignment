package com.yeonkyu.watchaassignment.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.listeners.SplashListener
import com.yeonkyu.watchaassignment.data.repository.SplashRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: SplashRepository) : ViewModel() {

    private var splashListener: SplashListener? = null

    fun setSplashListener(listener: SplashListener){
        splashListener = listener
    }

    fun initiate(){
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)

            val term = "greenday"
            val entity = "song"
            try {
                val response = repository.searchTrack(term,entity)
                Log.e("CHECK_TAG",response.resultCount.toString())
                Log.e("CHECK_TAG",response.results[0].trackName)
            }catch (e: Exception){
                Log.e("ERROR_TAG", "searchTrack error : $e")
            }

            splashListener?.onSplashFinish()
        }
    }

}