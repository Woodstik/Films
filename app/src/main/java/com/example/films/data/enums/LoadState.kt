package com.example.films.data.enums

import retrofit2.HttpException
import java.io.IOException

sealed class LoadState<out T> {
    class Error<T>(private val throwable: Throwable) : LoadState<T>() {
        fun reason(): ErrorReason {
            return when (throwable) {
                is IOException -> ErrorReason.NETWORK
                is HttpException -> ErrorReason.HTTP
                else -> ErrorReason.UNKNOWN
            }
        }
    }

    class Loading<T> : LoadState<T>()
    data class Data<T>(val data: T) : LoadState<T>()
}
