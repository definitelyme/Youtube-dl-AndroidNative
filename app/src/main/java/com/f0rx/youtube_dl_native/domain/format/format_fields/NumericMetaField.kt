package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class NumericMetaField {
    Height {
        override fun toString(): String = "height"
    },
    Width {
        override fun toString(): String = "width"
    },
    FileSize {
        override fun toString(): String = "filesize"
    },
    AudioVideoBitRate {
        override fun toString(): String = "tbr"
    },
    AudioBitRate {
        override fun toString(): String = "abr"
    },
    VideoBitRate {
        override fun toString(): String = "vbr"
    },
    AudioSamplingRate {
        override fun toString(): String = "asr"
    },
    FrameRate {
        override fun toString(): String = "fps"
    };

    abstract override fun toString(): String
}