package com.f0rx.youtube_dl_native.domain

import com.f0rx.youtube_dl_native.domain.Either.Left
import com.f0rx.youtube_dl_native.domain.Either.Right

sealed class Either<out L, out R> {
    data class Left<out L>(val f: L) : Either<L, Nothing>()
    data class Right<out R>(val r: R) : Either<Nothing, R>()
}

fun <L> left(value: L): Either<L, Nothing> = Left(value)

fun <R> right(value: R): Either<Nothing, R> = Right(value)

fun <R> either(action: () -> R): Either<Throwable, R> =
    try {
        right(action())
    } catch (e: Throwable) {
        error(e)
    }

fun <L, R> Either<L, R>.getOrElse(option: () -> R): R =
    try {
        if (this != null) option.invoke()
        else option.invoke()
    } catch (e: Throwable) {
        option.invoke()
    }

inline infix fun <L, R, UA> Either<L, R>.map(f: (R) -> UA): Either<L, UA> = when (this) {
    is Left -> this
    is Right -> Right(f(this.r))
}

infix fun <L, R, UA> Either<L, (R) -> UA>.apply(f: Either<L, R>): Either<L, UA> = when (this) {
    is Left -> this
    is Right -> f.map(this.r)
}

inline infix fun <L, R, UA> Either<L, R>.flatMap(f: (R) -> Either<L, UA>): Either<L, UA> =
    when (this) {
        is Left -> this
        is Right -> f(r)
    }

inline infix fun <L, Err, R> Either<L, R>.mapLeft(f: (L) -> Err): Either<Err, R> where Err : Exception =
    when (this) {
        is Left -> Left(f(error(this)))
        is Right -> this
    }

inline fun <L, R, UA> Either<L, R>.fold(f: (L) -> UA, r: (R) -> UA): UA = when (this) {
    is Left -> f(this.f)
    is Right -> r(this.r)
}