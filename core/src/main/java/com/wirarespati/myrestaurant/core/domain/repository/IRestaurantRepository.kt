package com.wirarespati.myrestaurant.core.domain.repository

import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {
    fun getAllRestaurant(): Flow<com.wirarespati.myrestaurant.core.data.Resource<List<Restaurant>>>

    fun getFavoriteRestaurant(): Flow<List<Restaurant>>

    suspend fun setFavoriteRestaurant(id: String)

    suspend fun searchRestaurant(query: String): Flow<com.wirarespati.myrestaurant.core.data.Resource<List<Restaurant>>>

    suspend fun getDetailRestaurant(query: String): Flow<com.wirarespati.myrestaurant.core.data.Resource<DetailRestaurant>>
    fun getFavoriteStatus(id: String): Flow<Boolean>
}