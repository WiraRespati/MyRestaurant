package com.wirarespati.myrestaurant.core.domain.usecase

import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantUseCase {
    fun getAllRestaurant(): Flow<Resource<List<Restaurant>>>
    fun getFavoriteRestaurant(): Flow<List<Restaurant>>
    suspend fun setFavoriteRestaurant(id: String)
    fun getFavoriteStatus(id: String): Flow<Boolean>
    suspend fun searchRestaurant(query: String): Flow<Resource<List<Restaurant>>>
    suspend fun getDetailRestaurant(query: String): Flow<Resource<DetailRestaurant>>
}