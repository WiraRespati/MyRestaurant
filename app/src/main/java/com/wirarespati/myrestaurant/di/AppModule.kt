package com.wirarespati.myrestaurant.di

import com.wirarespati.myrestaurant.core.domain.usecase.RestaurantInteractor
import com.wirarespati.myrestaurant.core.domain.usecase.RestaurantUseCase
import com.wirarespati.myrestaurant.ui.restaurant.detail.DetailViewModel
import com.wirarespati.myrestaurant.ui.restaurant.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RestaurantUseCase> { RestaurantInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
