package com.wirarespati.myrestaurant.core.utils

import com.wirarespati.myrestaurant.core.data.source.local.entity.RestaurantEntity
import com.wirarespati.myrestaurant.core.data.source.remote.response.Menus
import com.wirarespati.myrestaurant.core.data.source.remote.response.RestaurantsItem
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.model.DrinksItem
import com.wirarespati.myrestaurant.core.domain.model.FoodsItem
import com.wirarespati.myrestaurant.core.domain.model.Restaurant

object DataMapper {
    fun mapResponsesToEntities(input: List<RestaurantsItem?>): List<RestaurantEntity> {
        val restaurantList = ArrayList<RestaurantEntity>()
        input.map {
            val tourism = it?.id?.let { it1 ->
                RestaurantEntity(
                    id = it1,
                    pictureId = it.pictureId,
                    city = it.city,
                    name = it.name,
                    rating = it.rating,
                    description = it.description,
                    isFavorite = false
                )
            }
            if (tourism != null) {
                restaurantList.add(tourism)
            }
        }
        return restaurantList
    }

    fun mapResponseToDomain(input: RestaurantsItem): Restaurant {
        return Restaurant(
            id = input.id,
            pictureId = input.pictureId,
            city = input.city,
            name = input.name,
            rating = input.rating,
            description = input.description,
            isFavorite = false
        )
    }

    fun mapResponseToDomain(input: com.wirarespati.myrestaurant.core.data.source.remote.response.Restaurant): DetailRestaurant {
        return DetailRestaurant(
            id = input.id,
            pictureId = input.pictureId,
            city = input.city,
            name = input.name,
            rating = input.rating,
            menus = mapMenusToDomain(input.menus)
        )
    }

    private fun mapMenusToDomain(input: Menus?): com.wirarespati.myrestaurant.core.domain.model.Menus {
        val foods = input?.foods?.map { foodItem ->
            FoodsItem(
                name = foodItem?.name
            )
        }
        val drinks = input?.drinks?.map { drinkItem ->
            DrinksItem(
                name = drinkItem?.name
            )
        }
        return com.wirarespati.myrestaurant.core.domain.model.Menus(foods = foods, drinks = drinks)
    }


    fun mapEntitiesToDomain(input: List<RestaurantEntity>): List<Restaurant> =
        input.map {
            Restaurant(
                id = it.id,
                pictureId = it.pictureId,
                city = it.city,
                name = it.name,
                rating = it.rating,
                description = it.description,
                isFavorite = it.isFavorite
            )
        }
}