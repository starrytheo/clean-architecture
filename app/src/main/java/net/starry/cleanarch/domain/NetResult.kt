package net.starry.cleanarch.domain

sealed class NetResult<out MODEL> {
    data class Success<MODEL>(val response: MODEL) : NetResult<MODEL>()
    data class Error(val error: NetError) : NetResult<Nothing>()
}
