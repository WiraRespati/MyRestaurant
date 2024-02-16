package com.wirarespati.myrestaurant.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String? = null,
    val pictureId: String? = null,
    val city: String? = null,
    val name: String? = null,
    val rating: Float? = null,
    val description: String? = null,
    var isFavorite: Boolean = false
) : Parcelable