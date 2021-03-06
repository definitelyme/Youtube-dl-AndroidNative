package com.f0rx.youtube_dl_native.domain.format

import com.f0rx.youtube_dl_native.domain.format.format_fields.IBinder
import com.f0rx.youtube_dl_native.utils.padIf

class FormatBuilder(
    private val format: Format,
    private val binder: IBinder?,
    private val separator: FormatSeparator,
) {
    private constructor(builder: Builder) : this(builder.format, builder.binder, builder.separator)

    companion object {
        inline fun define(format: Format, block: Builder.() -> Unit): FormatBuilder =
            Builder(format = format).apply(block).build()
    }

    class Builder(
        var format: Format
    ) {
        var binder: IBinder? = null
        var separator: FormatSeparator = FormatSeparator.SquareBrackets

        fun build() = FormatBuilder(this)
    }

    override fun toString(): String =
        "$format${
            binder?.toString()?.padIf(
                start = separator.args().a,
                end = separator.args().b
            ) ?: ""
        }"
}