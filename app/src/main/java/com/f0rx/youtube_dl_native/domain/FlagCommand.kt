package com.f0rx.youtube_dl_native.domain

enum class FlagCommand : ICommand {
    AllFormats {
        override var flag: String = "--all-formats"
        override var computed: String? = null
    },

    GetFileName {
        override var flag: String = "--get-filename"
        override var computed: String? = null
    },

    PreferFreeFormats {
        override var flag: String = "--prefer-free-formats"
        override var computed: String? = null
    },

    RestrictFileNames {
        override var flag: String = "--restrict-filenames"
        override var computed: String? = null
    },

    ListFormats {
        override var flag: String = "-F"
        override var computed: String? = null
    };

    override fun toString(): String = flag
}