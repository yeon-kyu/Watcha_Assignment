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
    private var isSplashing = false

    //viewModel에서 SplashActivity의 매소드를 호출할 수 있도록 listener 인터페이스를 활용하였습니다.
    fun setSplashListener(listener: SplashListener){
        splashListener = listener
    }

    //splashActivity가 create()되었을때 호출되는 메소드입니다
    //splashActivity 화면에서 화면회전으로 인한 activity destroy()->recreate 될때
    //delay(2000) 및 api 호출을 중복으로 실행시키지 않게 isSplashing 변수를 이용하였습니다
    fun initiate(){
        if(!isSplashing) {
            isSplashing = true;
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)

//                val term = "greenday"
//                val entity = "song"
//                try {
//                    val response = repository.searchTrack(term, entity)
//                    Log.e("CHECK_TAG", response.resultCount.toString())
//                    Log.e("CHECK_TAG", response.results[0].trackName)
//                } catch (e: Exception) {
//                    Log.e("ERROR_TAG", "searchTrack error : $e")
//                }

                splashListener?.onSplashFinish()
                isSplashing = false
            }
        }
    }

}