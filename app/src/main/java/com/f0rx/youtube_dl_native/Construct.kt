package com.f0rx.youtube_dl_native

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import arrow.core.Either
import com.f0rx.youtube_dl_native.domain.FlagCommand
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.response.BaseResponse
import com.f0rx.youtube_dl_native.domain.response.CommandResponse
import com.f0rx.youtube_dl_native.domain.response.DoneResponse
import com.f0rx.youtube_dl_native.exceptions.ILibraryException
import com.f0rx.youtube_dl_native.exceptions.TaskException
import com.f0rx.youtube_dl_native.models.MediaMetadata
import com.f0rx.youtube_dl_native.utils.ifNullOrBlank
import com.f0rx.youtube_dl_native.utils.padIf
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLException
import com.yausername.youtubedl_android.YoutubeDLRequest
import java.util.regex.Matcher
import java.util.regex.Pattern

class Construct(override var showLogs: Boolean = true) : ILibrary {
    override lateinit var instance: YoutubeDL
    override lateinit var context: Context
    private var pattern = "\"([^\"]*)\"|(\\S+)"
    private var updating = false

    override fun initialize(context: Context): Either<ILibraryException, ILibrary> = try {
        this.context = context
        this.instance = YoutubeDL.getInstance()
        this.instance.init(context)
        Either.right(this)
    } catch (e: Throwable) {
        Log.e(ILibrary.kTag, "Failed to Initialize Youtube-dl Android", e)

        Either.left(
            TaskException.define("Failed to Initialize Youtube-dl Android") {}
        )
    }

    override fun metadata(url: Uri): Either<ILibraryException, MediaMetadata> = try {
        val info = instance.getInfo("$url")

        Either.right(MediaMetadata.fromData(raw = info))
    } catch (e: ILibraryException) {
        Either.left(e)
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

        if (showLogs) request.addOption(FlagCommand.Verbose.flag)

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
    } catch (e: ILibraryException) {
        Either.left(e)
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    override fun execute(
        url: Uri,
        commands: String?,
    ): Either<ILibraryException, BaseResponse> =
        execute(url = url, commands = commands, null)

    override fun execute(
        url: Uri,
        commands: String?,
        callback: ((Float, Long) -> Unit)?
    ): Either<ILibraryException, BaseResponse> = try {
        // Get Request instance
        val request = YoutubeDLRequest("$url")

        if (showLogs) request.addOption(FlagCommand.Verbose.flag)

        // Add Custom Flags
        /// NOTE:
        /// this is not the recommended way to add options/flags/url and might break in future
        /// use the constructor for url, addOption(key) for flags, addOption(key, value) for options
        val matcher: Matcher = Pattern.compile(pattern).matcher(commands ?: "")
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                request.addOption(matcher.group(1))
            } else {
                request.addOption(matcher.group(2))
            }
        }

        // Check if permission has been granted else throw exception
        checkHasStoragePermission()

        // Execute Request (async)
        val response = if (callback == null) instance.execute(request)
        else instance.execute(request, callback)

        // Return response (Right)
        Either.right(
            CommandResponse.define(
                message = "Successfully executed all commands!",
                response.command as ArrayList<String>,
                response.elapsedTime,
                response.out
            ) {
                error = response.err
                exitCode = response.exitCode
            }
        )
    } catch (e: ILibraryException) {
        Either.left(e)
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    override fun execute(
        url: Uri,
        commands: ArrayList<ICommand>,
    ): Either<ILibraryException, BaseResponse> =
        execute(url = url, commands = commands, null)

    override fun execute(
        url: Uri,
        commands: ArrayList<ICommand>,
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, BaseResponse> = try {
        // Map & fold() ICommand to String
        val builtCommands: String = commands.map { value ->
            value.flag.padIf(!value.computed.isNullOrEmpty(), end = " ") +
                    (value.computed ?: "")
        }.fold("") { acc, next -> "$acc $next" }

        // Execute
        execute(url, builtCommands, callback)
    } catch (e: ILibraryException) {
        Either.left(e)
    } catch (e: YoutubeDLException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    override fun update(): Either<ILibraryException, BaseResponse> = try {
        if (updating)
            Either.left(TaskException.define("Update already in progress") {})

        updating = true

        when (instance.updateYoutubeDL(context)) {
            YoutubeDL.UpdateStatus.ALREADY_UP_TO_DATE -> {
                updating = false
                Either.right(DoneResponse.define("Already up to date!") {})
            }
            YoutubeDL.UpdateStatus.DONE -> {
                updating = false
                Either.right(DoneResponse.define("Update successful!") {})
            }
            else -> {
                updating = false
                Either.right(DoneResponse.define("Successfully Updated!") {})
            }
        }
    } catch (e: YoutubeDLException) {
        updating = false
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    } catch (e: InterruptedException) {
        updating = false
        Either.left(TaskException.define(e.message) { cause = e.cause; trace = e.stackTrace })
    }

    private fun checkHasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                throw TaskException.define(
                    "^v^ Storage Permission required to execute Command!"
                ) {}
            }
        }
    }
}