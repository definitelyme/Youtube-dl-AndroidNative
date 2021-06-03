package com.f0rx.youtube_dl_native.domain

interface ICommand {
    var flag: String
    var computed: String?

    override fun toString(): String
}