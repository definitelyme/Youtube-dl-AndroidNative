package com.f0rx.youtube_dl_native.repositories

import android.net.Uri
import arrow.core.orNull
import com.f0rx.youtube_dl_native.ILibrary
import com.f0rx.youtube_dl_native.utils.padIf
import kotlin.io.path.Path
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension

abstract class BaseRepository(open var library: ILibrary) {
    var fileName: String? = null
    var extension: String? = null

    fun fileName(uri: String): String? {
        val metadata = library.metadata(Uri.parse(uri))

        metadata.fold(
            {},
        ) {
            fileName = it.fulltitle?.padIf(
                condition = !it.fulltitle.isNullOrBlank(),
                end = it.ext?.padIf(start = ".") ?: ""
            ) ?: it.title?.padIf(
                condition = !it.title.isNullOrBlank(),
                end = it.ext?.padIf(start = ".") ?: ""
            )
        }

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

        return metadata.orNull()?.ext?.padIf(start = ".")
    }
}