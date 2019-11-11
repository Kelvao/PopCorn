package com.kelvinievenes.popcorn.data.local.database

import com.kelvinievenes.popcorn.data.repository.base.ErrorCode
import com.kelvinievenes.popcorn.data.repository.base.PopCornException

suspend fun <T : Any?> runDatabaseTransaction(run: suspend () -> T): T {
    try {
        return run()
    } catch (e: Exception) {
        when (e) {
            is PopCornException -> throw PopCornException(e.errorCode)
            else -> throw PopCornException(ErrorCode.GENERIC_ERROR)
        }
    }
}