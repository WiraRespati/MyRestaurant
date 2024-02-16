package com.wirarespati.myrestaurant.core.domain.usecase

import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantInteractor(private val restaurantRepository: IRestaurantRepository) :
    RestaurantUseCase {
    override fun getAllRestaurant() = restaurantRepository.getAllRestaurant()

    override fun getFavoriteRestaurant() = restaurantRepository.getFavoriteRestaurant()

    override suspend fun setFavoriteRestaurant(id: String) =
        restaurantRepository.setFavoriteRestaurant(id)

    override fun getFavoriteStatus(id: String): Flow<Boolean> =
        restaurantRepository.getFavoriteStatus(id)


    override suspend fun searchRestaurant(query: String) =
        restaurantRepository.searchRestaurant(query)

    override suspend fun getDetailRestaurant(query: String): Flow<Resource<DetailRestaurant>> =
        restaurantRepository.getDetailRestaurant(query)


}