package com.f0rx.youtube_dl_native

import android.content.Context
import android.net.Uri
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.exceptions.TaskException
import com.f0rx.youtube_dl_native.models.MediaMetadata
import com.f0rx.youtube_dl_native.utils.ifNullOrBlank
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest


class MainLibrary : ILibrary {
    override lateinit var instance: YoutubeDL

    override fun initialize(context: Context): ILibrary {
        try {
            instance = YoutubeDL.getInstance()
            instance.init(context)
        } catch (e: Throwable) {
            throw TaskException.define("Failed to Initialize Youtube-dl Android") {}
        }

        return this
    }

    override fun metadata(url: Uri): MediaMetadata {
        val info = instance.getInfo("$url")



        return MediaMetadata.fromData(raw = info)
    }

    override fun metadata(
        url: Uri,
        commands: ArrayList<ICommand>,
    ): MediaMetadata {
        val request = YoutubeDLRequest("$url")

        // Add each command to YoutubeDLRequest
        commands.forEach { command ->
            command.computed?.let {
                request.addOption(command.flag, it)
            }

            command.computed.ifNullOrBlank {
                request.addOption(command.flag)
            }
        }

        val videoInfo = instance.getInfo(request)

        return MediaMetadata.fromData(videoInfo)
    }
}