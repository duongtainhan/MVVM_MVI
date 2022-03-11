package com.dienty.structure.data.response

import com.dienty.structure.data.model.Restaurant
import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("logo")
    val logo: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("phone_number")
    val phoneNumber: String?,
) {
    fun toRestaurant() = Restaurant(
        id = id.orEmpty(),
        name = name.orEmpty(),
        type = type.orEmpty(),
        logo = logo.orEmpty(),
        phone = phoneNumber.orEmpty(),
        address = address.orEmpty()
    )
}

fun List<RestaurantResponse>.toRestaurants(): List<Restaurant> {
    val restaurants = mutableListOf<Restaurant>()
    forEach {
        restaurants.add(it.toRestaurant())
    }
    return restaurants
}