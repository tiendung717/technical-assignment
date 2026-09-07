package com.android.app.common

import kotlinx.coroutines.CancellationException
import timber.log.Timber

sealed class ResultState<out T> {
    data object Idle: ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Failure(val throwable: Throwable) : ResultState<Nothing>()

    fun isLoading() = this is Loading
    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
    fun isIdle() = this is Idle
}

suspend fun <T> safeCall(block: suspend () -> T): ResultState<T> {
    return try {
        ResultState.Success(block())
    } catch (throwable: CancellationException) {
        throw throwable
    } catch (throwable: Throwable) {
        Timber.e(throwable, "Exception occurred")
        ResultState.Failure(throwable)
    }
}

