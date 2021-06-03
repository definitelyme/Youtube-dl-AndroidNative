package com.f0rx.youtube_dl_native.domain.format

enum class Format {
    Best {
        override fun toString(): String = "best"
    },
    Worst {
        override fun toString(): String = "worst"
    },
    BestVideo {
        override fun toString(): String = "bestvideo"
    },
    WorstVideo {
        override fun toString(): String = "worstvideo"
    },
    BestAudio {
        override fun toString(): String = "bestaudio"
    },
    WorstAudio {
        override fun toString(): String = "worstaudio"
    };

    abstract override fun toString(): String
}