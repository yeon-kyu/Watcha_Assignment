package com.yeonkyu.watchaassignment.di

import com.yeonkyu.watchaassignment.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
}