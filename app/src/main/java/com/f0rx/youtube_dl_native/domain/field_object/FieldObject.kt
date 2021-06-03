package com.f0rx.youtube_dl_native.domain.field_object

import com.f0rx.youtube_dl_native.domain.Either
import com.f0rx.youtube_dl_native.domain.left
import com.f0rx.youtube_dl_native.domain.right

abstract class FieldObject {
    fun parse(input: String): Either<FieldObjectException, FieldObject> = try {
        right(this)
    } catch (e: Exception) {
        left(FieldObjectException.invalid { })
    }

}