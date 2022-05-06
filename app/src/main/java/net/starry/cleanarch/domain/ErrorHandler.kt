package net.starry.cleanarch.domain

interface ErrorHandler {
    fun getError(throwable: Throwable): NetError
}
