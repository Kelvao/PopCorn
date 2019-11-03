package com.kelvinievenes.popcorn.data.repository.base

import com.google.gson.Gson
import com.kelvinievenes.popcorn.domain.model.ErrorMessage
import okhttp3.ResponseBody
import retrofit2.Response

fun <T : Any> performRequest(response: Response<T>, verifyBody: Boolean = true): Any {
    try {
        return if (response.isSuccessful) {
            if (verifyBody) {
                response.body()?.let {
                    it
                } ?: treatError(response.errorBody())
            } else {
                true
            }
        } else {
            treatError(response.errorBody())
        }
    } catch (e: Exception) {
        when (e) {
            /**
             * Include more cases if needed
             */
            is PopCornException -> throw PopCornException(
                e.errorCode
            )
            else -> throw PopCornException(ErrorCode.GENERIC_ERROR)
        }
    }
}

fun treatError(errorBody: ResponseBody? = null) {
    errorBody?.string()?.let { error ->
        /**
         * Customize error class depending project
         * Each project has it's own error structure, customize method to adapt
         */
        Gson().fromJson(error, ErrorMessage::class.java)?.let { wsError ->
            throw PopCornException(
                ErrorCode.fromString(
                    wsError.name
                )
            )
        }
    } ?: throw PopCornException(ErrorCode.GENERIC_ERROR)
}