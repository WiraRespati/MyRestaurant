package com.wirarespati.myrestaurant.favorite.di

import com.wirarespati.myrestaurant.favorite.ui.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}

