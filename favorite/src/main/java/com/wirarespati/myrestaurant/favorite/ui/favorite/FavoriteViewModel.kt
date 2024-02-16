package com.wirarespati.myrestaurant.favorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wirarespati.myrestaurant.core.domain.usecase.RestaurantUseCase


class FavoriteViewModel(restaurantUseCase: RestaurantUseCase) : ViewModel() {
    val favorite = restaurantUseCase.getFavoriteRestaurant().asLiveData()
}