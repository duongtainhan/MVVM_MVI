package com.dienty.structure.common

import com.dienty.structure.data.adapter.NetworkResponse
import com.dienty.structure.data.adapter.RemoteResponse
import com.dienty.structure.data.response.Resource
import kotlinx.coroutines.flow.*

inline fun <DB : Any, REMOTE : Any> networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<DB?>,
    crossinline processLocalResponse: suspend (DB) -> DB,
    crossinline shouldFetchFromRemote: (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: suspend () -> RemoteResponse<REMOTE>,
    crossinline processRemoteResponse: suspend (response: RemoteResponse<REMOTE>) -> DB?,
    crossinline saveToLocal: suspend (DB?) -> Unit = { },
    crossinline onFetchFailed: (errorBody: String?, statusCode: Int) -> Unit = { _: String?, _: Int ->  }
) = flow {
    val localData = fetchFromLocal().first()
    if (shouldFetchFromRemote(localData)) {
        fetchFromRemote().let { apiResponse ->
            when (apiResponse) {
                is NetworkResponse.Success -> {
                    val processedData = processRemoteResponse(apiResponse)
                    saveToLocal(processedData)
                    emitAll(fetchFromLocal().map {
                        Resource.Success(processLocalResponse(it!!))
                    })
                }
                is NetworkResponse.ApiError -> {
                    onFetchFailed(apiResponse.body.message, apiResponse.body.code?.toInt() ?: 0)
                    emitAll(fetchFromLocal().map {
                        Resource.Error(apiResponse.body.message, apiResponse.body.code, it)
                    })
                }
                is NetworkResponse.NetworkError -> {
                    onFetchFailed(apiResponse.error.message, 113)
                    emitAll(fetchFromLocal().map {
                        Resource.Error(apiResponse.error.message, "113", it)
                    })
                }
                is NetworkResponse.UnknownError -> {
                    onFetchFailed(apiResponse.error.message, 0)
                    emitAll(fetchFromLocal().map {
                        Resource.Error(apiResponse.error.message, "0", it)
                    })
                }
            }
        }
    } else {
        emitAll(fetchFromLocal().map { Resource.Success(processLocalResponse(it!!)) })
    }
}