package com.dienty.structure.data.repository

import com.dienty.structure.data.adapter.NetworkResponse
import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.data.response.Resource
import com.dienty.structure.data.response.toRestaurants
import com.dienty.structure.data.service.ExampleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val service: ExampleService) :
    ExampleRepository {

    override fun getRestaurants() = flow<Resource<List<Restaurant>>> {
        when (val response = service.getRestaurants()) {
            is NetworkResponse.Success -> {
                emit(Resource.Success(data = response.body.toRestaurants()))
            }
            else -> throw response.toError()
        }
    }.onStart {
        emit(Resource.Loading())
    }.onCompletion {
        emit(Resource.Done())
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(Dispatchers.IO)
}