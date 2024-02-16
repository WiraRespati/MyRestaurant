package com.wirarespati.myrestaurant.favorite.ui.favorite

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
import com.wirarespati.myrestaurant.favorite.di.favoriteModule
import com.wirarespati.myrestaurant.favorite.ui.theme.MyRestaurantTheme
import com.wirarespati.myrestaurant.ui.about.AboutScreen
import com.wirarespati.myrestaurant.ui.restaurant.detail.DetailScreen
import com.wirarespati.myrestaurant.ui.restaurant.home.HomeScreen
import org.koin.core.context.loadKoinModules

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
        setContent {
            val navController: NavHostController = rememberNavController()
            MyRestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "favorite") {
                        composable("favorite") {
                            FavoriteScreen(navController = navController)
                        }
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("about") {
                            AboutScreen(navController = navController)
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
