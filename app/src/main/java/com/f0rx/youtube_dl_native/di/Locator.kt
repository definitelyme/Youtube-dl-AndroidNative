package com.f0rx.youtube_dl_native.di

import arrow.core.orNull
import com.f0rx.youtube_dl_native.Construct
import com.f0rx.youtube_dl_native.repositories.DownloadRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dependencies = module {
    single { Construct().initialize(androidContext()).orNull() }

    single { DownloadRepository(get()) }
}