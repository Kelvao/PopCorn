package com.kelvinievenes.popcorn.data.repository.base

import java.lang.RuntimeException

/**
 * PopCornException, Exception used to describe errors occurred when try to use
 * in data layer.
 */
class PopCornException(var errorCode: ErrorCode, var errorMessage : String? = "") : RuntimeException()
