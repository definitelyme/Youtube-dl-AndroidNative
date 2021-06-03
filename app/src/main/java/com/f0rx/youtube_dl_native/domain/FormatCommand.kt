package com.f0rx.youtube_dl_native.domain

import com.f0rx.youtube_dl_native.domain.format.FormatBuilder

class FormatCommand private constructor(
    override var computed: String?,
) : ICommand {
    override var flag: String = "-f"
    private constructor(builder: Builder) : this(builder.computed.toString())

    companion object {
        inline fun build(initial: FormatBuilder, block: Builder.() -> Unit): FormatCommand =
            Builder(initial).apply(block).generate()
    }

    class Builder(initial: FormatBuilder) {
        var computed: StringBuilder = StringBuilder(initial.toString())
            private set

        fun plus() = apply { this.computed.append("+") }

        fun or() = apply { this.computed.append("/") }

        fun append(instance: FormatBuilder) = apply { this.computed.append("$instance") }

        fun generate() = FormatCommand(this)
    }

    override fun toString(): String = computed.toString()
}