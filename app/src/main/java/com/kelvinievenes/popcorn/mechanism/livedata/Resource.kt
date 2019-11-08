package com.kelvinievenes.popcorn.mechanism.livedata

import androidx.lifecycle.MutableLiveData

data class Resource<out DataType>(
    val status: Status,
    val message: String? = null,
    val errorCode: Int? = null,
    val data: DataType? = null,
    val errorMessageId: Int? = null
) {

    companion object {

        fun <DataType> loading(): Resource<DataType> =
            Resource(Status.LOADING)

        fun <DataType> success(data: DataType? = null): Resource<DataType> =
            Resource(
                Status.SUCCESS, data = data
            )

        fun <DataType> successNextPage(data: DataType? = null): Resource<DataType> =
            Resource(Status.SUCCESS_NEXT_PAGE, data = data)

        fun <DataType> empty(): Resource<DataType> =
            Resource(Status.EMPTY)

        fun <DataType> emptyNextPage(): Resource<DataType> =
            Resource(Status.EMPTY_NEXT_PAGE)

        fun <DataType> error(
            message: String? = null, errorCode: Int? = null,
            errorMessageId: Int? = null, data: DataType? = null
        ): Resource<DataType> =
            Resource(
                Status.ERROR,
                message = message,
                errorCode = errorCode,
                errorMessageId = errorMessageId,
                data = data
            )

        fun <DataType> errorNextPage(
            message: String? = null, errorCode: Int? = null,
            errorMessageId: Int? = null, data: DataType? = null
        ): Resource<DataType> =
            Resource(
                Status.ERROR_NEXT_PAGE,
                message = message,
                errorCode = errorCode,
                errorMessageId = errorMessageId,
                data = data
            )
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    EMPTY,
    ERROR,
    SUCCESS_NEXT_PAGE,
    EMPTY_NEXT_PAGE,
    ERROR_NEXT_PAGE
}

typealias MutableLiveDataResource<DataType> = MutableLiveData<Resource<DataType>>