package com.yeonkyu.watchaassignment.viewmodels

import androidx.lifecycle.ViewModel
import com.yeonkyu.watchaassignment.data.listeners.SplashListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private var splashListener: SplashListener? = null
    private var isSplashing = false
    var isDestroyed = false //splashActivity에서 앱을 종료했을때 TrackListActivity를 실행시키지 못하게 flag를 두었습니다

    //viewModel에서 SplashActivity의 매소드를 호출할 수 있도록 listener 인터페이스를 활용하였습니다.
    fun setSplashListener(listener: SplashListener){
        splashListener = listener
    }

    //splashActivity가 create()되었을때 호출되는 메소드입니다
    //splashActivity 화면에서 화면회전 등으로 activity가 destroy()->recreate 될때
    //delay(2000) 및 api 호출을 중복으로 실행시키지 않게 isSplashing 변수를 이용하였습니다
    fun initiate(){
        if(!isSplashing) {
            isSplashing = true;
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)

                isSplashing = false
                if(!isDestroyed)
                    splashListener?.onSplashFinish()
            }
        }
    }

}