package com.dienty.structure.data.repository

import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.data.response.Resource
import kotlinx.coroutines.flow.*

interface ExampleRepository {
    fun getRestaurants(): Flow<Resource<List<Restaurant>>>
}