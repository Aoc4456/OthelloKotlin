package com.aoc4456.othellokotlin

import android.app.Application
import timber.log.Timber

class OthelloApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}