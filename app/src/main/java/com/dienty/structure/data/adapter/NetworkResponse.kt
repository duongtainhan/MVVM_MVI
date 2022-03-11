package com.dienty.structure.data.adapter

import com.dienty.structure.data.response.Error
import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Error> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Error>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing, Nothing>()

    fun toError(): Throwable {
        return when (this) {
            is NetworkError -> error
            is UnknownError -> error
            is ApiError -> Throwable(body.message)
            else -> Throwable()
        }
    }
}