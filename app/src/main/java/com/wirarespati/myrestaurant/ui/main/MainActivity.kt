package com.wirarespati.myrestaurant.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wirarespati.myrestaurant.ui.about.AboutScreen
import com.wirarespati.myrestaurant.ui.restaurant.detail.DetailScreen
import com.wirarespati.myrestaurant.ui.restaurant.home.HomeScreen
import com.wirarespati.myrestaurant.ui.theme.MyRestaurantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            MyRestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("about") {
                            AboutScreen(navController = navController)
                        }
                        composable("favorite") {
                            val uri = Uri.parse("myrestaurant://favorite")
                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                        }
                        composable(
                            route = "detail/{itemId}",
                            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                        ) {
                            DetailScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
