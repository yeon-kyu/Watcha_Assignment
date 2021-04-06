package com.yeonkyu.watchaassignment.di

import com.yeonkyu.watchaassignment.data.repository.SplashRepository
import com.yeonkyu.watchaassignment.data.repository.TrackRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SplashRepository(get()) }
    single { TrackRepository(get()) }
}