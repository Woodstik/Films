package com.example.films.data.enums

import retrofit2.HttpException
import java.io.IOException

sealed class LoadState<in T> {
    data class Error(private val throwable: Throwable) : LoadState<Any?>() {
        fun reason(): ErrorReason {
            return when (throwable) {
                is IOException -> ErrorReason.NETWORK
                is HttpException -> ErrorReason.HTTP
                else -> ErrorReason.UNKNOWN
            }
        }
    }

    object Loading : LoadState<Any?>()
    data class Data<T>(val data: T) : LoadState<T>()
}
