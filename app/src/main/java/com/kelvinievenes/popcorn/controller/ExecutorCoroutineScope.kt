package com.kelvinievenes.popcorn.controller

import com.kelvinievenes.popcorn.data.repository.base.PopCornException

interface ExecutorCoroutineScope {
    fun cancelJobs()
    fun runCoroutine(run: suspend () -> Unit): ConcurrencyContinuation =
        ConcurrencyContinuation(run)

    infix fun ConcurrencyContinuation.onError(onError: (PopCornException) -> Unit)
}

inline class ConcurrencyContinuation(val action: suspend () -> Unit)