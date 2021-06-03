package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class MediaExtension {
    MPEG {
        override fun toString(): String = "3gp"
    },
    AAC {
        override fun toString(): String = "aac"
    },
    FLV {
        override fun toString(): String = "flv"
    },
    M4A {
        override fun toString(): String = "m4a"
    },
    MP3 {
        override fun toString(): String = "mp3"
    },
    MP4 {
        override fun toString(): String = "mp4"
    },
    OGG {
        override fun toString(): String = "ogg"
    },
    WAV {
        override fun toString(): String = "wav"
    },
    WEBM {
        override fun toString(): String = "webm"
    };

    abstract override fun toString(): String
}