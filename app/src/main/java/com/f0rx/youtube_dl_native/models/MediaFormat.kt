package com.f0rx.youtube_dl_native.models

import com.yausername.youtubedl_android.mapper.VideoFormat

class MediaFormat(
    private var format: String?,
    private var formatId: String?,
    private var formatNote: String?,
    private var ext: String?,
    private var preference: Int?,
    private var vcodec: String?,
    private var acodec: String?,
    private var width: Int?,
    private var height: Int?,
    private var filesize: Long?,
    private var url: String?,
) {
    companion object {
        fun fromData(instance: VideoFormat): MediaFormat {
            return MediaFormat(
                format = instance.format,
                formatId = instance.formatId,
                formatNote = instance.formatNote,
                ext = instance.ext,
                preference = instance.preference,
                vcodec = instance.vcodec,
                acodec = instance.acodec,
                width = instance.width,
                height = instance.height,
                filesize = instance.filesize,
                url = instance.url
            )
        }
    }

    override fun toString(): String = "MediaFormat(" +
            "format: $format, format-id: $formatId, format-note: $formatNote, " +
            "ext: $ext, preference: $preference, vcodec: $vcodec, acodec: $acodec," +
            "width: $width, height: $height, file-size: $filesize, url: $url" +
            ")"
}