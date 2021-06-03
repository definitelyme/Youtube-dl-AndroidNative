package com.f0rx.youtube_dl_native.repositories

import android.net.Uri
import arrow.core.Either
import com.f0rx.youtube_dl_native.ILibrary
import com.f0rx.youtube_dl_native.domain.FlagCommand
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.OutputCommand
import com.f0rx.youtube_dl_native.exceptions.ILibraryException
import com.f0rx.youtube_dl_native.models.CommandResponse
import com.f0rx.youtube_dl_native.utils.padIf
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

class DownloadRepository(override var library: ILibrary) : BaseRepository(library) {
    fun download(
        uri: Uri,
        path: File,
        commands: ArrayList<ICommand> = arrayListOf(),
        callback: ((Float, Long) -> Unit),
        name: String? = null
    ): Either<ILibraryException, CommandResponse> {
        val filename =
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
        commands.add(OutputCommand("${path.absolutePath}${File.separator}$filename"))
        commands.add(FlagCommand.AllFormats)
        commands.add(FlagCommand.PreferFreeFormats)

        return library.execute(uri, commands, callback)
    }
}