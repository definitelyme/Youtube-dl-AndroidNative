package com.f0rx.youtube_dl_native.domain.response

class DoneResponse(
    override var message: String,
    override var commands: ArrayList<String>?,
    override var elapsedTime: Long?,
    override var out: String?,
    override var exitCode: Int?,
    override var error: String?,
) : BaseResponse {
    private constructor(builder: Builder) : this(
        builder.message,
        builder.commands, builder.elapsedTime, builder.out,
        builder.exitCode, builder.error
    )

    companion object {
        inline fun define(message: String, block: Builder.() -> Unit): BaseResponse =
            Builder(message = message).apply(block).build()
    }

    class Builder(
        var message: String,
    ) {
        var commands: ArrayList<String>? = null
        var elapsedTime: Long? = null
        var out: String? = null
        var exitCode: Int? = 0
        var error: String? = null

        fun build() = DoneResponse(this)
    }
}