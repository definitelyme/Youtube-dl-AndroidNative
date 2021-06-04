package com.f0rx.youtube_dl_native.domain.format

import arrow.core.Tuple2

enum class FormatSeparator {
    Parentheses {
        override fun args(): Tuple2<String, String> = Tuple2("(", ")")
    },
    SquareBrackets {
        override fun args(): Tuple2<String, String> = Tuple2("[", "]")
    };

    abstract fun args(): Tuple2<String, String>
}