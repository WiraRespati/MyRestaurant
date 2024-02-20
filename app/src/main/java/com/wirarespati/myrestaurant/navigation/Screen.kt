package com.wirarespati.myrestaurant.navigation

sealed class Screen {
    data object Detail : Screen() {
        fun createRoute(itemId: String) = "detail/$itemId"
    }
}
