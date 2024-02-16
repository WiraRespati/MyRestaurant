package com.wirarespati.myrestaurant.ui.restaurant.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DetailViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<Resource<DetailRestaurant>>()
    var uiState: LiveData<Resource<DetailRestaurant>> = _uiState

    private val _isFavorite = MutableLiveData<Boolean>()
    var isFavorite: LiveData<Boolean> = _isFavorite

    private var isDetailLoaded = false

    fun getDetailRestaurant(itemId: String) {
        if (!isDetailLoaded) {
            viewModelScope.launch {
                try {
                    val detail = restaurantUseCase.getDetailRestaurant(itemId).firstOrNull()
                    if (detail != null) {
                        _uiState.value = Resource.Success(detail.data!!)
                    } else {
                        _uiState.value = Resource.Error("Detail not found")
                    }
                    isDetailLoaded = true
                } catch (e: Exception) {
                    _uiState.value = Resource.Error(e.message ?: "Error occurred")
                }
            }
        }
    }

    fun setFavoriteRestaurant(id: String) {
        viewModelScope.launch {
            restaurantUseCase.setFavoriteRestaurant(id)
        }
    }

    fun isFavorite(id: String) {
        viewModelScope.launch {
            restaurantUseCase.getFavoriteStatus(id).collect { isFavorite ->
                _isFavorite.value = isFavorite
            }
        }
    }

}



