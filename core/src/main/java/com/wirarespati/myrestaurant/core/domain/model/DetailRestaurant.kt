package com.wirarespati.myrestaurant.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRestaurant(
    val address: String? = null,
    val pictureId: String? = null,
    val city: String? = null,
    val name: String? = null,
    val rating: Double? = null,
    val id: String? = null,
    val menus: Menus? = null
) : Parcelable

@Parcelize
data class DrinksItem(
    val name: String? = null
) : Parcelable

@Parcelize
data class FoodsItem(
    val name: String? = null
) : Parcelable

@Parcelize
data class Menus(
    val foods: List<FoodsItem?>? = null, val drinks: List<DrinksItem?>? = null
) : Parcelable