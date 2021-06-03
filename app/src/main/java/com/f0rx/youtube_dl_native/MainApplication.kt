package com.f0rx.youtube_dl_native

import android.app.Application
import com.f0rx.youtube_dl_native.di.dependencies
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(dependencies)
        }
    }
}