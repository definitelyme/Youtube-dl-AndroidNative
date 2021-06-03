package com.f0rx.youtube_dl_native.models

import com.yausername.youtubedl_android.mapper.VideoInfo

class MediaMetadata
    (
    var id: String?,
    var fulltitle: String?,
    var title: String?,
    var uploadDate: String?,
    var displayId: String?,
    var duration: Int?,
    var description: String?,
    var thumbnail: String?,
    var license: String?,
    var viewCount: String?,
    var likeCount: String?,
    var dislikeCount: String?,
    var repostCount: String?,
    var averageRating: String?,
    var uploaderId: String?,
    var uploader: String?,
    var playerUrl: String?,
    var webpageUrl: String?,
    var webpageUrlBasename: String?,
    var resolution: String?,
    var width: Int?,
    var height: Int?,
    var format: String?,
    var formatId: String?,
    var ext: String?,
    var httpHeaders: Map<String, String?>?,
    var categories: ArrayList<String?>?,
    var tags: ArrayList<String?>?,
    var manifestUrl: String?,
    var url: String?,
    var formats: ArrayList<MediaFormat>?,
    var requestedFormats: ArrayList<MediaFormat>?,
    var thumbnails: ArrayList<MediaThumbnail>?,
) {
    companion object {
        fun fromData(raw: VideoInfo): MediaMetadata {
            return MediaMetadata(
                id = raw.id,
                fulltitle = raw.fulltitle,
                title = raw.title,
                uploadDate = raw.uploadDate,
                displayId = raw.displayId,
                duration = raw.duration,
                description = raw.description,
                thumbnail = raw.thumbnail,
                license = raw.license,
                viewCount = raw.viewCount,
                likeCount = raw.likeCount,
                dislikeCount = raw.dislikeCount,
                repostCount = raw.repostCount,
                averageRating = raw.averageRating,
                uploaderId = raw.uploaderId,
                uploader = raw.uploader,
                playerUrl = raw.playerUrl,
                webpageUrl = raw.webpageUrl,
                webpageUrlBasename = raw.webpageUrlBasename,
                resolution = raw.resolution,
                width = raw.width,
                height = raw.height,
                format = raw.format,
                formatId = raw.formatId,
                ext = raw.ext,
                httpHeaders = raw.httpHeaders,
                categories = raw.categories,
                tags = raw.tags,
                manifestUrl = raw.manifestUrl,
                url = raw.url,
                formats = raw.formats?.mapNotNull(MediaFormat.Companion::fromData) as ArrayList<MediaFormat>?,
                requestedFormats = raw.requestedFormats?.mapNotNull(MediaFormat.Companion::fromData) as ArrayList<MediaFormat>?,
                thumbnails = raw.thumbnails?.mapNotNull {
                    MediaThumbnail(
                        it.id,
                        it.url
                    )
                } as ArrayList<MediaThumbnail>?,
            )
        }
    }

    override fun toString(): String = "VideoMetadata(" +
            "id: $id, full-title: $fulltitle, title: $title, upload-date: $uploadDate, " +
            "duration: $duration, description: $description, " +
            "webpage-url: $webpageUrl, webpage-basename: $webpageUrlBasename, " +
            "resolution: $resolution, " +
            "width: $width, height: $height, extension: $ext " +
            "player-url: $playerUrl, tags: ${tags?.size}, manifest-url: $manifestUrl " +
            "url: $url, formats: ${formats?.size}, requestedFormats: ${requestedFormats?.size}, " +
            "thumbnails: ${thumbnails?.size}" +
            ")"
}