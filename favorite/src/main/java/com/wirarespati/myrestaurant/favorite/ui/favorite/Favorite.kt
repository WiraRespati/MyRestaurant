package com.wirarespati.myrestaurant.favorite.ui.favorite

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.wirarespati.myrestaurant.R
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import com.wirarespati.myrestaurant.navigation.Screen
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
) {
    val viewModel: FavoriteViewModel = get()
    val favoriteList by viewModel.favorite.observeAsState()

    Scaffold(
        topBar = {

            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ), title = {
                Text(
                    text = "Favorite Restaurants", modifier = Modifier.padding(top = 4.dp)
                )

            }, navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("home")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            favoriteList?.let { list ->
                LazyColumn(
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(list) { favoriteItem ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .clip(RoundedCornerShape(65.dp))
                                .background(MaterialTheme.colorScheme.onPrimary)
                        ) {
                            FavoriteListItem(list = favoriteItem,
                                modifier = Modifier.animateItemPlacement(
                                    tween(
                                        durationMillis = 100
                                    )
                                ),
                                onItemClick = { itemId ->
                                    navController.navigate(Screen.Detail.createRoute(itemId))
                                })
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FavoriteListItem(
    list: Restaurant,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .clickable {
                list.id?.let { onItemClick(it) }
            }) {
        AsyncImage(
            model = "https://restaurant-api.dicoding.dev/images/large/${list.pictureId}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.onPrimary)

        )
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Row(
                modifier = modifier.padding(start = 16.dp, top = 32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_restaurant), contentDescription = null
                )
                Text(
                    text = list.name.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                        .size(24.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = list.rating.toString(),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_city),
                    contentDescription = null,
                    modifier = modifier.size(24.dp)
                )
                Text(
                    text = list.city.toString(),
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, start = 4.dp)
                        .size(24.dp)
                )
            }
        }
    }
}
