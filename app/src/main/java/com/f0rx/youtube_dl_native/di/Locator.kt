package com.f0rx.youtube_dl_native.di

import com.f0rx.youtube_dl_native.repositories.DownloadRepository
import com.f0rx.youtube_dl_native.MainLibrary
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dependencies = module {
    single { MainLibrary().initialize(androidContext()) }

    single { DownloadRepository(get()) }
}