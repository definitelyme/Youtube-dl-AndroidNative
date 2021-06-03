package com.f0rx.youtube_dl_native.repositories

import android.net.Uri
import com.f0rx.youtube_dl_native.ILibrary
import com.f0rx.youtube_dl_native.domain.FlagCommand
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.OutputCommand
import com.f0rx.youtube_dl_native.exceptions.DownloadException
import com.f0rx.youtube_dl_native.models.CommandResponse
import com.f0rx.youtube_dl_native.utils.ifNullOrBlank
import com.f0rx.youtube_dl_native.utils.padIf
import com.yausername.youtubedl_android.YoutubeDLException
import com.yausername.youtubedl_android.YoutubeDLRequest
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

class DownloadRepository(override var library: ILibrary) : IRepository() {
    fun start(
        uri: Uri,
        path: File,
        commands: ArrayList<ICommand> = arrayListOf(),
        callback: ((Float, Long) -> Unit),
        name: String? = null
    ): CommandResponse {
        val request = YoutubeDLRequest("$uri")

        val _fileName =
            extension("$uri")?.let {
                name?.let { it1 ->
                    Path(it1).nameWithoutExtension.padIf(
                        condition = !name.isNullOrEmpty(),
                        end = it,
                    )
                }
            }
                ?: fileName("$uri")

        // Add to List of commands
        commands.add(OutputCommand("${path.absolutePath}${File.separator}$_fileName"))
        commands.add(FlagCommand.AllFormats)
        commands.add(FlagCommand.PreferFreeFormats)

        // Add each command to YoutubeDLRequest
        commands.forEach { command ->
            command.computed?.let {
                request.addOption(command.flag, it)
            }

            command.computed.ifNullOrBlank {
                request.addOption(command.flag)
            }
        }

//        library.

//        request.addOption("--all-formats")
//        request.addOption("-F")

        return try {
            val response = library.instance.execute(request, callback)

            CommandResponse.define(
                response.command as ArrayList<String>, response.elapsedTime,
                response.out
            ) {
                error = response.err
                exitCode = response.exitCode
            }
        } catch (e: YoutubeDLException) {
            throw DownloadException.define(e.message) {}
        } catch (e: InterruptedException) {
            throw DownloadException.define(e.message) {}
        }
    }
}