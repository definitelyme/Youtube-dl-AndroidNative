package com.f0rx.youtube_dl_native.repositories

import android.net.Uri
import com.f0rx.youtube_dl_native.ILibrary
import com.f0rx.youtube_dl_native.utils.padIf
import kotlin.io.path.Path
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension

abstract class IRepository {
    protected abstract var library: ILibrary
    var fileName: String? = null
    var extension: String? = null

    fun fileName(uri: String): String? {
        val metadata = library.metadata(Uri.parse(uri))

        fileName = metadata.fulltitle?.padIf(
            condition = !metadata.fulltitle.isNullOrBlank(),
            end = metadata.ext?.padIf(start = ".") ?: ""
        ) ?: metadata.title?.padIf(
            condition = !metadata.title.isNullOrBlank(),
            end = metadata.ext?.padIf(start = ".") ?: ""
        )

        return fileName
    }

    fun nameWithoutExtension(uri: String): String? {
        if (fileName != null) {
            val relativePath = Path("$fileName")
            return relativePath.nameWithoutExtension
        }

        val filename = fileName(uri)
        return if (filename != null) Path(filename).nameWithoutExtension
        else null
    }

    fun extension(uri: String): String? {
        if (fileName != null) {
            val relativePath = Path("$fileName")
            extension = relativePath.extension
            return extension
        }

        val metadata = library.metadata(Uri.parse(uri))

        return metadata.ext?.padIf(start = ".")
    }
}