package com.example.questionnaire.base

sealed class Response<T>() {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val message: String, val data: T? = null) : Response<T>()
    data class Loading<T>(val data: T? = null) : Response<T>()
}