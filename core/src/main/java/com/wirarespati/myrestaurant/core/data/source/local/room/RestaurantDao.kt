package com.wirarespati.myrestaurant.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wirarespati.myrestaurant.core.data.source.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    fun getAllRestaurant(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM restaurant where isFavorite = 1")
    fun getFavoriteRestaurant(): Flow<List<RestaurantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: List<RestaurantEntity>)

    @Query("UPDATE restaurant SET isFavorite = CASE WHEN isFavorite = 0 THEN 1 ELSE 0 END WHERE id = :id")
    suspend fun updateFavoriteRestaurant(id: String)

    @Query("SELECT isFavorite FROM restaurant WHERE id = :id")
    fun getFavoriteStatus(id: String): Flow<Boolean>
}