package com.wirarespati.myrestaurant.core.data.source.local.entity

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "restaurant")
data class RestaurantEntity(
    @SuppressLint("KotlinNullnessAnnotation") @PrimaryKey
    @NonNull
    @ColumnInfo("id")
    val id: String,

    @ColumnInfo("pictureId")
    val pictureId: String? = null,

    @ColumnInfo("city")
    val city: String? = null,

    @ColumnInfo("name")
    val name: String? = null,

    @ColumnInfo("rating")
    val rating: Float? = null,

    @ColumnInfo("description")
    val description: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

) : Parcelable
