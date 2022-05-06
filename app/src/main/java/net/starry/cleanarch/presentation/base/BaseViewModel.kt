package net.starry.cleanarch.presentation.base

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import net.starry.cleanarch.domain.NetError
import net.starry.cleanarch.domain.NetResult
import net.starry.cleanarch.presentation.model.PopupMessage

abstract class BaseViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    private val _showAlertDialog = SingleLiveEvent<PopupMessage>()
    val showAlertDialog: LiveData<PopupMessage> = _showAlertDialog

    private val _showRetryDialog = SingleLiveEvent<Retry>()
    val showRetryDialog: LiveData<Retry> = _showRetryDialog

    protected fun showAlertDialog(popup: PopupMessage) {
        _showAlertDialog.value = popup
    }

    protected fun <T> Flow<NetResult<T>>.onSuccess(
        success: (suspend (T) -> Unit)? = null
    ) = onEach {
        if (it is NetResult.Success) success?.invoke(it.response)
    }

    protected fun <T> Flow<NetResult<T>>.onFailure(
        failure: (suspend (NetError.BadRequest) -> Unit)? = null
    ) = onEach {
        if (it is NetResult.Error && it.error is NetError.BadRequest)
            failure?.invoke(it.error as NetError.BadRequest)
    }

    protected fun <T> Flow<NetResult<T>>.commonErrorHandler(
        retryAction: RetryInvokable? = null
    ) = onEach {
        handleError(it, retryAction)
    }

    protected suspend fun <T> Flow<NetResult<T>>.call() = collect()

    protected suspend fun <T> Flow<NetResult<T>>.load(
        loading: ((Boolean) -> Unit)? = null
    ) = onStart {
        if (loading != null) loading.invoke(true) else _loading.value = true
    }.onCompletion {
        if (loading != null) loading.invoke(false) else _loading.value = false
    }.collect()

    private fun handleError(result: NetResult<*>, action: RetryInvokable?) {
        if (result !is NetResult.Error) return

        when (result.error) {
            is NetError.Network -> {
                _showRetryDialog.value =
                    Retry(action, PopupMessage("network error", "network error"))
            }
            is NetError.InternalServer -> {
                _showRetryDialog.value =
                    Retry(action, PopupMessage("server error", "server error"))
            }
            is NetError.Timeout -> {
                _showRetryDialog.value =
                    Retry(action, PopupMessage("timeout", "network timeout"))
            }
            is NetError.Unknown -> {
                _showAlertDialog.value =
                    PopupMessage("unknown error", "unknown error")
            }
        }
    }
}
