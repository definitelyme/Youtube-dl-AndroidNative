package com.f0rx.youtube_dl_native.utils

inline fun <C, Return> C.ifNullOrBlank(default: () -> Return): Return where C : Return {
    if (this == null) return default.invoke()

    return when (this) {
        is Iterable<*> -> {
            return if (this.count() == 0) default.invoke()
            else this
        }
        is String -> {
            return if (this.isEmpty()) default.invoke()
            else this
        }
        else -> this
    }
}

infix fun <P1, IP, R> Function1<P1, IP>.andThen(f: (IP) -> R): (P1) -> R = forwardCompose(f)

infix fun <P1, IP, R> Function1<P1, IP>.forwardCompose(f: (IP) -> R): (P1) -> R =
    { p1: P1 -> f(this(p1)) }

infix fun <IP, R, P1> Function1<IP, R>.compose(f: (P1) -> IP): (P1) -> R = { p1: P1 -> this(f(p1)) }

infix fun (() -> Unit).unless(condition: Boolean) {
    if (condition) this.invoke()
}
