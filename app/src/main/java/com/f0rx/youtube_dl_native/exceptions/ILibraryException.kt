package com.f0rx.youtube_dl_native.exceptions

import java.lang.Exception

abstract class ILibraryException(
    override var message: String?,
    override var cause: Throwable?
) : Exception(message, cause) {
    abstract var code: String?
    abstract var trace: Array<StackTraceElement>
}