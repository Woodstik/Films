package com.example.films.data.enums

sealed class LoadState<out T> {
    class Error<T> : LoadState<T>()
    class Loading<T>: LoadState<T>()
    data class Data<T>(val data: T) : LoadState<T>()
}
