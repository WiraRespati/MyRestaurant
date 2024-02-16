package com.wirarespati.myrestaurant.core.data.source.remote.network

import com.wirarespati.myrestaurant.core.data.source.remote.response.DetailResponse
import com.wirarespati.myrestaurant.core.data.source.remote.response.RestaurantResponse
import com.wirarespati.myrestaurant.core.data.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    suspend fun getListRestaurant(): RestaurantResponse

    @GET("/detail/{id}")
    suspend fun getRestaurantDetail(
        @Path("id")
        id: String
    ): DetailResponse

    @GET("/search")
    suspend fun searchRestaurants(@Query("q") query: String): SearchResponse
}