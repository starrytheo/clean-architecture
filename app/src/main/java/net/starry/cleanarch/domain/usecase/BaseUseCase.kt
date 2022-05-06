package net.starry.cleanarch.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import net.starry.cleanarch.domain.ErrorHandler
import net.starry.cleanarch.domain.NetResult

abstract class BaseUseCase<out Type : Any, in Params> {
    abstract suspend fun execute(params: Params): Type

    suspend operator fun invoke(
        params: Params
    ) = execute(params)

    protected fun <T> Flow<T>.toResult(errorHandler: ErrorHandler) = map {
        NetResult.Success(it) as NetResult<T>
    }.catch { cause ->
        // if(cause is HttpException && cause.code() = // )

        emit(NetResult.Error(errorHandler.getError(cause)))
    }
}
