package com.wirarespati.myrestaurant.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("restaurant")
    val restaurant: Restaurant? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)


data class Restaurant(


    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("pictureId")
    val pictureId: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("menus")
    val menus: Menus? = null
)


data class DrinksItem(

    @field:SerializedName("name")
    val name: String? = null
)

data class FoodsItem(

    @field:SerializedName("name")
    val name: String? = null
)

data class Menus(

    @field:SerializedName("foods")
    val foods: List<FoodsItem?>? = null,

    @field:SerializedName("drinks")
    val drinks: List<DrinksItem?>? = null
)
