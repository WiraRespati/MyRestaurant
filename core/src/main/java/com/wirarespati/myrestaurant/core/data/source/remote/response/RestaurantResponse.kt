package com.wirarespati.myrestaurant.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("restaurants")
    val restaurants: List<RestaurantsItem?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class RestaurantsItem(

    @field:SerializedName("pictureId")
    val pictureId: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Float? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null
)
