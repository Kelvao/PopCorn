package com.kelvinievenes.popcorn.controller

import com.kelvinievenes.popcorn.data.repository.base.PopCornException
import kotlinx.coroutines.Deferred


interface ExecutorCoroutineScope {
    fun cancelJobs()
    fun runCoroutine(run: suspend () -> Unit): ConcurrencyContinuation =
        ConcurrencyContinuation(run)

    suspend fun <T> runAsync(run: suspend () -> T): Deferred<T>
    infix fun ConcurrencyContinuation.onError(onError: (PopCornException) -> Unit)
}

inline class ConcurrencyContinuation(val action: suspend () -> Unit)