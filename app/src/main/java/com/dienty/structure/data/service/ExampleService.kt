package com.dienty.structure.data.service

import com.dienty.structure.data.adapter.RemoteResponse
import com.dienty.structure.data.response.RestaurantResponse
import retrofit2.http.GET

interface ExampleService {
    @GET("restaurant/random_restaurant?size=100")
    suspend fun getRestaurants(): RemoteResponse<List<RestaurantResponse>>
}