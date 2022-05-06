package net.starry.cleanarch.presentation.base

import net.starry.cleanarch.presentation.model.PopupMessage

typealias RetryInvokable = () -> Unit

data class Retry(
    val action: RetryInvokable?,
    val message: PopupMessage
)
