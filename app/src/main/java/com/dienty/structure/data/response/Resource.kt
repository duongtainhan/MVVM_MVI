package com.dienty.structure.data.response

sealed class Resource<out T> {
    data class Success<T : Any>(val data: T) : Resource<T>()
    data class Error<T : Any>(val errorMsg: String? = "", val errorCode: String? = "", val data: T? = null) : Resource<T>()
    data class Loading<T : Any>(val data: T? = null) : Resource<T>()
    data class Done<T : Any>(val data: T? = null) : Resource<T>()
}
