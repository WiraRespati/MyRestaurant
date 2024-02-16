package com.wirarespati.myrestaurant.navigation

sealed class Screen {
    object Detail : Screen() {
        fun createRoute(itemId: String) = "detail/$itemId"
    }
}
