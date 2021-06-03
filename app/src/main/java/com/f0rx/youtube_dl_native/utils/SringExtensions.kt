package com.f0rx.youtube_dl_native.utils

fun CharSequence.padIf(
    condition: Boolean? = null,
    start: String = "",
    end: String = "",
    length: Int = 1
): String {
    val _condition = condition ?: this.isNotEmpty()

    return if (_condition) {
        val sb = StringBuilder(length)
        for (i in 1..length)
            sb.append(start)

        val sb2 = StringBuilder(length)
        for (i in 1..length)
            sb2.append(end)

        "$sb$this$sb2"
    } else this.toString()
}
