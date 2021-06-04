package com.f0rx.youtube_dl_native.domain.response

class CommandResponse private constructor(
    override var message: String,
    override var commands: ArrayList<String>?,
    override var elapsedTime: Long?,
    override var out: String?,
    override var exitCode: Int?,
    override var error: String?,
): BaseResponse {
    private constructor(builder: Builder) : this(
        builder.message,
        builder.commands, builder.elapsedTime, builder.out,
        builder.exitCode, builder.error
    )

    companion object {
        inline fun define(
            message: String,
            commands: ArrayList<String>,
            elapsedTime: Long,
            out: String,
            block: Builder.() -> Unit
        ): CommandResponse =
            Builder(
                message = message,
                commands = commands, elapsedTime = elapsedTime,
                out = out
            ).apply(block).build()
    }

    class Builder(
        var message: String,
        var commands: ArrayList<String>,
        var elapsedTime: Long,
        var out: String,
    ) {
        var exitCode: Int? = 0
        var error: String? = null

        fun build() = CommandResponse(this)
    }
}