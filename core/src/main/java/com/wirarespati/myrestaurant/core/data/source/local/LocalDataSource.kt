package com.wirarespati.myrestaurant.core.data.source.local

import com.wirarespati.myrestaurant.core.data.source.local.entity.RestaurantEntity
import com.wirarespati.myrestaurant.core.data.source.local.room.RestaurantDao

import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val restaurantDao: RestaurantDao) {

    fun getAllRestaurant(): Flow<List<RestaurantEntity>> = restaurantDao.getAllRestaurant()

    fun getFavoriteRestaurant(): Flow<List<RestaurantEntity>> =
        restaurantDao.getFavoriteRestaurant()

    suspend fun insertRestaurant(restaurantList: List<RestaurantEntity>) =
        restaurantDao.insertRestaurant(restaurantList)

    suspend fun setFavoriteRestaurant(id: String) {
        restaurantDao.updateFavoriteRestaurant(id)
    }

    fun getFavoriteStatus(id: String): Flow<Boolean> = restaurantDao.getFavoriteStatus(id)
}