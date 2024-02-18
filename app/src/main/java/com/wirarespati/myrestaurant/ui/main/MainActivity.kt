package com.wirarespati.myrestaurant.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
import com.wirarespati.myrestaurant.R
import com.wirarespati.myrestaurant.ui.about.AboutScreen
import com.wirarespati.myrestaurant.ui.restaurant.detail.DetailScreen
import com.wirarespati.myrestaurant.ui.restaurant.home.HomeScreen
import com.wirarespati.myrestaurant.ui.theme.MyRestaurantTheme

class MainActivity : ComponentActivity() {
    private lateinit var broadcastReceiver: BroadcastReceiver
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
    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        val message = context.resources.getString(R.string.power_connected)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        val message = context.resources.getString(R.string.power_disconnected)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }
}
