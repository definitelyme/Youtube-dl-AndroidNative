package com.f0rx.youtube_dl_native

import android.content.Context
import android.net.Uri
import arrow.core.Either
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.exceptions.ILibraryException
import com.f0rx.youtube_dl_native.exceptions.TaskException
import com.f0rx.youtube_dl_native.models.CommandResponse
import com.f0rx.youtube_dl_native.models.MediaMetadata
import com.f0rx.youtube_dl_native.utils.ifNullOrBlank
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLException
import com.yausername.youtubedl_android.YoutubeDLRequest

class Construct : ILibrary {
    override lateinit var instance: YoutubeDL

    override fun initialize(context: Context): Either<ILibraryException, ILibrary> = try {
        instance = YoutubeDL.getInstance()
        instance.init(context)
        Either.right(this)
    } catch (e: Throwable) {
        print(e.message)
        Either.left(
            TaskException.define("Failed to Initialize Youtube-dl Android") {}
        )
    }

    override fun metadata(url: Uri): Either<ILibraryException, MediaMetadata> = try {
        val info = instance.getInfo("$url")

        Either.right(MediaMetadata.fromData(raw = info))
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    override fun metadata(
        url: Uri,
        commands: ArrayList<ICommand>,
    ): Either<ILibraryException, MediaMetadata> = try {
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

        Either.right(MediaMetadata.fromData(raw = videoInfo))
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    override fun execute(
        url: Uri,
        commands: ArrayList<ICommand>,
    ): Either<ILibraryException, CommandResponse> =
        execute(url = url, commands = commands, null)

    override fun execute(
        url: Uri,
        commands: ArrayList<ICommand>,
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, CommandResponse> = try {
        // Get Request instance
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

        // Execute Request (async)
        val response = if (callback == null) instance.execute(request)
        else instance.execute(request, callback)

        // Return response (Right)
        Either.right(CommandResponse.define(
            response.command as ArrayList<String>,
            response.elapsedTime,
            response.out
        ) {
            error = response.err
            exitCode = response.exitCode
        })
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }
}