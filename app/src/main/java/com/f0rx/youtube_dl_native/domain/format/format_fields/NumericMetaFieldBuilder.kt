package com.f0rx.youtube_dl_native.domain.format.format_fields

class NumericMetaFieldBuilder private constructor(
    private val field: NumericMetaField,
    private val operator: NumericOperator,
    private val value: Int,
    private val dataSize: MediaDataSize?,
) : IBinder {
    private constructor(builder: Builder) : this(
        builder.field,
        builder.operator,
        builder.value,
        builder.dataSize
    )

    companion object {
        inline fun define(
            field: NumericMetaField,
            operator: NumericOperator,
            value: Int,
            block: Builder.() -> Unit
        ): NumericMetaFieldBuilder =
            Builder(field, operator, value).apply(block).build()
    }

    class Builder(
        val field: NumericMetaField,
        val operator: NumericOperator,
        val value: Int
    ) {
        var dataSize: MediaDataSize? = null

        fun build() = NumericMetaFieldBuilder(this)
    }

    override fun toString(): String =
        "$field$operator$value${dataSize?.toString() ?: ""}"
}