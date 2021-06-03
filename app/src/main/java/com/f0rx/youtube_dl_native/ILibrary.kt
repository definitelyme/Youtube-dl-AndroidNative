package com.f0rx.youtube_dl_native

import android.content.Context
import android.net.Uri
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.models.MediaMetadata
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest

interface ILibrary {
    companion object {
        const val kTag: String = "library-tag"
    }

    var instance: YoutubeDL

    fun initialize(context: Context): ILibrary

    fun metadata(url: Uri): MediaMetadata

    fun metadata(
        url: Uri,
        commands: ArrayList<ICommand>
    ): MediaMetadata
}