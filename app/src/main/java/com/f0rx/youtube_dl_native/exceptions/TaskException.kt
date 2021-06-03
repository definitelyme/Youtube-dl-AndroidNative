package com.f0rx.youtube_dl_native.exceptions

class TaskException private constructor(
    override var code: String?,
    override var message: String?,
) : ILibraryException(message) {
    private constructor(builder: Builder) : this(builder.message, builder.code)

    companion object {
        inline fun define(
            message: String?,
            block: Builder.() -> Unit
        ): ILibraryException =
            Builder(message = message).apply(block).build()
    }

    class Builder(
        var message: String?,
    ) {
        var code: String? = null

        fun build() = TaskException(this)
    }
}