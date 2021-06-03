package com.f0rx.youtube_dl_native.exceptions

abstract class ILibraryException(
    override var message: String?
) : Exception(message) {
    abstract var code: String?
}