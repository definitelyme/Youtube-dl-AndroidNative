package com.f0rx.youtube_dl_native.models

class MediaThumbnail(
    private val id: String?,
    private var url: String?,
) {
    override fun toString(): String = "MediaThumbnail(id: $id, url: $url)"
}