package com.f0rx.youtube_dl_native.domain.field_object

import arrow.core.Either

abstract class FieldObject {
    fun parse(input: String): Either<FieldObjectException, FieldObject> = try {
        Either.right(this)
    } catch (e: Exception) {
        Either.left(FieldObjectException.invalid { })
    }

}