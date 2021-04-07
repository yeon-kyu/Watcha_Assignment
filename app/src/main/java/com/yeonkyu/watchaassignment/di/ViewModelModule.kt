package com.yeonkyu.watchaassignment.di

import com.yeonkyu.watchaassignment.viewmodels.FavoritesViewModel
import com.yeonkyu.watchaassignment.viewmodels.SplashViewModel
import com.yeonkyu.watchaassignment.viewmodels.TrackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { TrackViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
}