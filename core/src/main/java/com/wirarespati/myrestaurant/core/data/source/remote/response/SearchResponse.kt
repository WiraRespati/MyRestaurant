package com.wirarespati.myrestaurant.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("founded") val founded: Int,
    @SerializedName("restaurants") val restaurants: List<RestaurantsItem>
)
