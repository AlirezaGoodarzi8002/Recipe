package com.alireza8002.recipe.data.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    
    sealed class Error : ResultWrapper<Nothing>() {
        data class NetworkError(val message: String = "Network connection error") : Error()
        data class HttpError(val code: Int, val message: String) : Error()
        data class UnknownError(val message: String = "Unknown error occurred") : Error()
    }
}
