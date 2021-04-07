package com.yeonkyu.watchaassignment.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.data.listeners.SplashListener
import com.yeonkyu.watchaassignment.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity(), SplashListener {
    private val mViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()

        mViewModel.initiate()
    }

    private fun setupView(){
        setContentView(R.layout.activity_splash)
    }

    private fun setupViewModel(){
        mViewModel.setSplashListener(this)
    }

    override fun onSplashFinish() {
        //onSplashFinish 를 호출하는 코드가 메인 외의 스레드에 있기 때문에
        //메인 스레드에서 호출될 수 있도록 액티비티  생성 코드를 runOnUiThread 내부에 넣었습니다
        runOnUiThread {
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mViewModel.isDestroyed = true
    }
}