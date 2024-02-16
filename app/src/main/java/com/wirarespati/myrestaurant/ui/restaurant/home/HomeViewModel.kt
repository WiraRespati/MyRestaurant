package com.wirarespati.myrestaurant.ui.restaurant.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import com.wirarespati.myrestaurant.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {
    private val _result = MutableLiveData<Resource<List<Restaurant>>>()
    var result: LiveData<Resource<List<Restaurant>>> = _result


    fun getAllRestaurant() {
        viewModelScope.launch {
            try {
                restaurantUseCase.getAllRestaurant().collect { listRestaurant ->
                    _result.postValue(listRestaurant)
                }
            } catch (e: Exception) {
                _result.postValue(Resource.Error(e.message ?: "Error occurred"))
            }
        }
    }

    fun searchRestaurant(query: String) {
        viewModelScope.launch {
            try {
                restaurantUseCase.searchRestaurant(query).collect { listRestaurant ->
                    _result.postValue(listRestaurant)
                }
            } catch (e: Exception) {
                _result.postValue(Resource.Error(e.message ?: "Error occurred"))
            }
        }
    }
}
