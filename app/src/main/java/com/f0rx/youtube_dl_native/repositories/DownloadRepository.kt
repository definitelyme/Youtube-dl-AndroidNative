package com.f0rx.youtube_dl_native.repositories

import android.net.Uri
import arrow.core.Either
import com.f0rx.youtube_dl_native.ILibrary
import com.f0rx.youtube_dl_native.domain.FlagCommand
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.OutputCommand
import com.f0rx.youtube_dl_native.domain.response.CommandResponse
import com.f0rx.youtube_dl_native.exceptions.ILibraryException
import com.f0rx.youtube_dl_native.utils.padIf
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

class DownloadRepository(override var library: ILibrary) : BaseRepository(library) {
    fun download(
        uri: Uri,
        downloadPath: File,
        name: String? = null,
        commands: String = "",
    ): Either<ILibraryException, CommandResponse> =
        download(uri, downloadPath, name, commands, null)

    fun download(
        uri: Uri,
        downloadPath: File,
        name: String? = null,
        commands: String = "",
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, CommandResponse> {
        val filename = getFileName(uri = uri, custom = name, null)

        // Add Output command (for download requests only)
        val concat = "$commands -o ${downloadPath.absolutePath}${File.separator}$filename"

        return library.execute(
            uri, concat, callback
        ).map {
            CommandResponse.define(
                message = it.message,
                commands = it.commands!!,
                elapsedTime = it.elapsedTime!!,
                out = it.out!!
            ) {
                exitCode = it.exitCode
                error = it.error
            }
        }
    }

    fun download(
        uri: Uri,
        downloadPath: File,
        name: String? = null,
        commands: ArrayList<ICommand>?,
    ): Either<ILibraryException, CommandResponse> =
        download(uri, downloadPath, name, commands, null)

    fun download(
        uri: Uri,
        downloadPath: File,
        name: String? = null,
        commands: ArrayList<ICommand>?,
        callback: ((Float, Long) -> Unit)?,
    ): Either<ILibraryException, CommandResponse> {
        val filename = getFileName(uri = uri, custom = name, commands = commands)

        val overloadedCommands = commands ?: arrayListOf()

        // Add Output command (for download requests only)
        overloadedCommands.add(
            OutputCommand("${downloadPath.absolutePath}${File.separator}$filename")
        )

        return library.execute(uri, overloadedCommands, callback).map {
            CommandResponse.define(
                message = it.message,
                commands = it.commands!!,
                elapsedTime = it.elapsedTime!!,
                out = it.out!!
            ) {
                exitCode = it.exitCode
                error = it.error
            }
        }
    }

    private fun getFileName(
        uri: Uri,
        custom: String? = null,
        commands: ArrayList<ICommand>? = arrayListOf(),
    ): String {
        return extension("$uri")?.let { ext ->
            custom?.let { it ->
                Path(it).nameWithoutExtension.padIf(
                    condition = !custom.isNullOrEmpty(),
                    end = ext,
                )
            }
        }
            ?: when {
                commands != null && (commands.contains(FlagCommand.AllFormats) ||
                        commands.contains(FlagCommand.PreferFreeFormats))
                -> {
                    "%(title)s-%(format)s-%(format_id)s.%(ext)s"
                }
                else -> "%(title)s.%(ext)s"
            }
    }
}