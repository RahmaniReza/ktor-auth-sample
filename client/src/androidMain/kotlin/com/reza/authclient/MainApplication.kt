package com.reza.authclient

import android.app.Application
import com.reza.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}