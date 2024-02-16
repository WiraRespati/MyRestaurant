package com.wirarespati.myrestaurant.core.data.source.remote

import android.util.Log
import com.wirarespati.myrestaurant.core.data.source.remote.network.ApiResponse
import com.wirarespati.myrestaurant.core.data.source.remote.network.ApiService
import com.wirarespati.myrestaurant.core.data.source.remote.response.Restaurant
import com.wirarespati.myrestaurant.core.data.source.remote.response.RestaurantsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getListRestaurant(): Flow<ApiResponse<List<RestaurantsItem?>>> {
        return flow {
            try {
                val response = apiService.getListRestaurant()
                val dataArray = response.restaurants
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response.restaurants))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRestaurantDetail(id: String): Flow<ApiResponse<Restaurant?>> {
        return flow {
            try {
                val response = apiService.getRestaurantDetail(id)
                val dataArray = response.restaurant
                if (dataArray != null) {
                    emit(ApiResponse.Success(response.restaurant))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchRestaurant(query: String): Flow<ApiResponse<List<RestaurantsItem?>>> {
        return flow {
            try {
                val response = apiService.searchRestaurants(query)
                val dataArray = response.restaurants
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.restaurants))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}