package com.f0rx.youtube_dl_native.exceptions

class TaskException private constructor(
    override var code: String?,
    override var message: String?,
    override var cause: Throwable?,
    override var trace: Array<StackTraceElement>,
) : ILibraryException(message, cause) {
    private constructor(builder: Builder) : this(
        builder.code,
        builder.message,
        builder.cause,
        builder.trace
    )

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
        var cause: Throwable? = null
        lateinit var trace: Array<StackTraceElement>

        fun build() = TaskException(this)
    }
}