package com.f0rx.youtube_dl_native.models

class CommandResponse private constructor(
    var commands: ArrayList<String>,
    var elapsedTime: Long,
    var out: String,
    var exitCode: Int?,
    var error: String?,
) {
    private constructor(builder: Builder) : this(
        builder.commands, builder.elapsedTime, builder.out,
        builder.exitCode, builder.error
    )

    companion object {
        inline fun define(
            commands: ArrayList<String>,
            elapsedTime: Long,
            out: String,
            block: Builder.() -> Unit
        ): CommandResponse =
            Builder(
                commands = commands, elapsedTime = elapsedTime,
                out = out
            ).apply(block).build()
    }

    class Builder(
        var commands: ArrayList<String>,
        var elapsedTime: Long,
        var out: String,
    ) {
        var exitCode: Int? = 0
        var error: String? = null

        fun build() = CommandResponse(this)
    }
}