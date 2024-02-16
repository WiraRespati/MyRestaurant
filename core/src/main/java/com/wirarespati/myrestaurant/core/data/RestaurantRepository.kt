package com.wirarespati.myrestaurant.core.data

import com.wirarespati.myrestaurant.core.data.source.local.LocalDataSource
import com.wirarespati.myrestaurant.core.data.source.remote.RemoteDataSource
import com.wirarespati.myrestaurant.core.data.source.remote.network.ApiResponse
import com.wirarespati.myrestaurant.core.data.source.remote.response.RestaurantsItem
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import com.wirarespati.myrestaurant.core.domain.repository.IRestaurantRepository
import com.wirarespati.myrestaurant.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RestaurantRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRestaurantRepository {
    override fun getAllRestaurant(): Flow<Resource<List<Restaurant>>> =
        object : NetworkBoundResource<List<Restaurant>, List<RestaurantsItem?>>() {
            override fun loadFromDB(): Flow<List<Restaurant>> {
                return localDataSource.getAllRestaurant().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Restaurant>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RestaurantsItem?>>> =
                remoteDataSource.getListRestaurant()

            override suspend fun saveCallResult(data: List<RestaurantsItem?>) {
                val restaurantList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRestaurant(restaurantList)
            }
        }.asFlow()

    override fun getFavoriteRestaurant(): Flow<List<Restaurant>> {
        return localDataSource.getFavoriteRestaurant().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override suspend fun setFavoriteRestaurant(id: String) {
        localDataSource.setFavoriteRestaurant(id)
    }

    override fun getFavoriteStatus(id: String): Flow<Boolean> =
        localDataSource.getFavoriteStatus(id)


    override suspend fun searchRestaurant(query: String): Flow<Resource<List<Restaurant>>> {
        return remoteDataSource.searchRestaurant(query).map { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val restaurants = apiResponse.data.map { restaurantItem ->
                        DataMapper.mapResponseToDomain(restaurantItem!!)
                    }

                    Resource.Success(data = restaurants)
                }

                is ApiResponse.Error -> {
                    Resource.Error(message = apiResponse.errorMessage)
                }

                is ApiResponse.Empty -> {
                    Resource.Error(message = "No data available")
                }
            }
        }.catch { e ->
            emit(
                Resource.Error(
                    message = e.message ?: "An error occurred"
                )
            )
        }
    }

    override suspend fun getDetailRestaurant(query: String): Flow<Resource<DetailRestaurant>> {
        return remoteDataSource.getRestaurantDetail(query)
            .map { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val detailRestaurant = DataMapper.mapResponseToDomain(response.data!!)
                        Resource.Success(detailRestaurant)
                    }

                    is ApiResponse.Empty -> Resource.Error("Detail restaurant not found")
                    is ApiResponse.Error -> Resource.Error("Error occurred: ${response.errorMessage}")
                }
            }
    }

}