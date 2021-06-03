package com.f0rx.youtube_dl_native.domain.format.format_fields

class StringMetaFieldBuilder private constructor(
    private val field: StringMetaField,
    private val operator: StringOperator,
    private val value: Any,
) : IBinder {
    private constructor(builder: Builder) : this(
        builder.field,
        builder.operator,
        builder.value,
    )

    companion object {
        inline fun define(
            field: StringMetaField,
            operator: StringOperator,
            value: Any,
            block: Builder.() -> Unit
        ): StringMetaFieldBuilder =
            Builder(field, operator, value).apply(block).build()
    }

    class Builder(
        val field: StringMetaField,
        val operator: StringOperator,
        val value: Any
    ) {
        fun build() = StringMetaFieldBuilder(this)
    }

    override fun toString(): String = "$field$operator$value"
}