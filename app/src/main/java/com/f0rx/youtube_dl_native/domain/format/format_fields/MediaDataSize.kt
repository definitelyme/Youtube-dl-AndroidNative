package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class MediaDataSize {
    Byte {
        override fun toString(): String = "B"
    },
    Kilobyte {
        override fun toString(): String = "K"
    },
    Megabyte {
        override fun toString(): String = "M"
    },
    Gigabyte {
        override fun toString(): String = "G"
    },
    Terabyte {
        override fun toString(): String = "T"
    };

    abstract override fun toString(): String
}