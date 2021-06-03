package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class StringMetaField {
    Extension {
        override fun toString(): String = "ext"
    },
    AudioCodec {
        override fun toString(): String = "acodec"
    },
    VideoCodec {
        override fun toString(): String = "vcodec"
    },
    ContainerFormat {
        override fun toString(): String = "container"
    },
    Protocol {
        override fun toString(): String = "protocol"
    },
    FormatID {
        override fun toString(): String = "format_id"
    },
    Language {
        override fun toString(): String = "language"
    };

    abstract override fun toString(): String
}