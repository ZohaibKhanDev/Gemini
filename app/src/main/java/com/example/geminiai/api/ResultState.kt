package com.example.geminiai.api

import okhttp3.Response

sealed class ResultState<out T> {
    object Loading:ResultState<Nothing>()
    data class Success<T>(val response: T):ResultState<T>()
    data class Error(val error:Throwable):ResultState<Nothing>()
}