package com.example.android.dessertpusher

import android.app.Application
import timber.log.Timber

class PusherApplication : Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}