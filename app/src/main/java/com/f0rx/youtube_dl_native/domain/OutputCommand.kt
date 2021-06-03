package com.f0rx.youtube_dl_native.domain

import com.f0rx.youtube_dl_native.domain.ICommand

class OutputCommand(
    override var computed: String?,
) : ICommand {
    override var flag: String = "-o"

    override fun toString(): String = computed.toString()
}