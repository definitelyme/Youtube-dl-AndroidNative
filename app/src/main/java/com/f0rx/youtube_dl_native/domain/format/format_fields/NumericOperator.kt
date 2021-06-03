package com.f0rx.youtube_dl_native.domain.format.format_fields

enum class NumericOperator {
    Equals {
        override fun toString(): String = "="
    },
    NotEqualTo {
        override fun toString(): String = "!="
    },
    GreaterThan {
        override fun toString(): String = ">"
    },
    LessThan {
        override fun toString(): String = "<"
    },
    GreaterThanOrEqualTo {
        override fun toString(): String = ">="
    },
    LessThanOrEqualTo {
        override fun toString(): String = "<="
    };

    abstract override fun toString(): String
}