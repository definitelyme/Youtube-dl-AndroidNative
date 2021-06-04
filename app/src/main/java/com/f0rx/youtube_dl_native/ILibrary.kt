package com.f0rx.youtube_dl_native

import android.content.Context
import android.net.Uri
import arrow.core.Either
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.response.BaseResponse
import com.f0rx.youtube_dl_native.exceptions.ILibraryException
import com.f0rx.youtube_dl_native.models.MediaMetadata
import com.yausername.youtubedl_android.YoutubeDL
import java.io.File

interface ILibrary {
    companion object {
        const val kTag: String = "library-tag"
    }

    var instance: YoutubeDL
    var context: Context
    var showLogs: Boolean

    fun initialize(context: Context): Either<ILibraryException, ILibrary>

    fun metadata(url: Uri): Either<ILibraryException, MediaMetadata>

    fun metadata(
        url: Uri,
        commands: ArrayList<ICommand>
    ): Either<ILibraryException, MediaMetadata>

    fun execute(
        url: Uri,
        commands: String?,
    ): Either<ILibraryException, BaseResponse>

    fun execute(
        url: Uri,
        commands: String?,
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, BaseResponse>

    fun execute(
        url: Uri,
        commands: ArrayList<ICommand> = arrayListOf(),
    ): Either<ILibraryException, BaseResponse>

    fun execute(
        url: Uri,
        commands: ArrayList<ICommand> = arrayListOf(),
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, BaseResponse>

    fun update(): Either<ILibraryException, BaseResponse>
}