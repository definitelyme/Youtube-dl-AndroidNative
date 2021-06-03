package com.f0rx.youtube_dl_native.domain.field_object

class FieldObjectException(var message: String) {
    private constructor(builder: Builder) : this(builder.message)

    companion object {
        inline fun empty(block: Builder.() -> Unit): FieldObjectException =
            Builder(message = "Field is required!").apply(block).generate()

        inline fun invalid(block: Builder.() -> Unit): FieldObjectException =
            Builder(message = "Provide a valid input!").apply(block).generate()
    }

    class Builder(val message: String) {
        fun generate() = FieldObjectException(this)
    }
}