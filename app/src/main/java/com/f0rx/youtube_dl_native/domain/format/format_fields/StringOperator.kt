package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class StringOperator {
    Equals {
        override fun toString(): String = "="
    },
    NotEqualTo {
        override fun toString(): String = "!="
    },
    StartsWith {
        override fun toString(): String = "^="
    },
    DoesNotStartWith {
        override fun toString(): String = "!^="
    },
    EndsWith {
        override fun toString(): String = "\$="
    },
    DoesNotEndWith {
        override fun toString(): String = "!\$="
    },
    Contains {
        override fun toString(): String = "*="
    },
    DoesNotContain {
        override fun toString(): String = "!*="
    };

    abstract override fun toString(): String
}