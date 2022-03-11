package com.dienty.structure.data.useCase

import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.data.repository.ExampleRepository
import com.dienty.structure.data.response.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExampleUseCase @Inject constructor(private val repository: ExampleRepository) {
    fun getRestaurants() : Flow<Resource<List<Restaurant>>> = repository.getRestaurants()
}