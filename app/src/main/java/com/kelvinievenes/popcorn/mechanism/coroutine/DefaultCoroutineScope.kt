package com.kelvinievenes.popcorn.mechanism.coroutine

import com.kelvinievenes.popcorn.data.repository.base.PopCornException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DefaultCoroutineScope : ExecutorCoroutineScope, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    override fun cancelJobs() {
        coroutineContext.cancel()
    }

    override infix fun ConcurrencyContinuation.onError(onError: (PopCornException) -> Unit) {
        initCoroutine(this.action, onError)
    }

    private fun initCoroutine(run: suspend () -> Unit, onError: (PopCornException) -> Unit = {}) =
        launch {
            try {
                run()
            } catch (e: PopCornException) {
                onError(e)
            }
        }
}

fun getCoroutineScope(): DefaultCoroutineScope =
    DefaultCoroutineScope()