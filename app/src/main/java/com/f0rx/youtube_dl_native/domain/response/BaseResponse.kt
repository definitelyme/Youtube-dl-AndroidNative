package com.f0rx.youtube_dl_native.domain.response

interface BaseResponse {
    var message: String
    var commands: ArrayList<String>?
    var elapsedTime: Long?
    var out: String?
    var exitCode: Int?
    var error: String?
}