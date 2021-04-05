package com.yeonkyu.watchaassignment.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yeonkyu.watchaassignment.R
import com.yeonkyu.watchaassignment.data.listeners.SplashListener
import com.yeonkyu.watchaassignment.databinding.ActivitySplashBinding
import com.yeonkyu.watchaassignment.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity(), SplashListener {
    private  lateinit var mBinding : ActivitySplashBinding
    private val mViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()

    }

    private fun setupView(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

    }
    private fun setupViewModel(){
        mViewModel.setSplashListener(this)
    }

    override fun onSplashFinish() {
        runOnUiThread {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}