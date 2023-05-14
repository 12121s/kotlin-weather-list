package com.illis.weatherlist

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeatherListApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: WeatherListApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    init{
        instance = this
        Timber.plant(Timber.DebugTree())
    }

}