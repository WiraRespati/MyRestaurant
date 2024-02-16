package com.wirarespati.myrestaurant.ui.restaurant.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.wirarespati.myrestaurant.R
import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.Restaurant
import com.wirarespati.myrestaurant.navigation.Screen
import org.koin.androidx.compose.get


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    val viewModel: HomeViewModel = get()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getAllRestaurant()
    }
    val result by viewModel.result.observeAsState()
    when (val uiState = result) {
        is Resource.Loading -> {
            LoadingItem()
        }

        is Resource.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = modifier.background(MaterialTheme.colorScheme.secondary),
                        title = {
                            Text("My Restaurants")
                        },
                        actions = {
                            IconButton(onClick = {
                                 navController.navigate("favorite")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    tint = Color.Red,
                                    contentDescription = "favorite",
                                    modifier = modifier.testTag("favoriteButton")
                                )
                            }
                            IconButton(onClick = { navController.navigate("about") }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "about_page",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            modifier.padding(end = 10.dp)
                        },
                    )
                },
            ) {
                Column(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {
                            viewModel.searchRestaurant(searchQuery)
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .testTag("searchBar")
                    )
                    LazyColumn(
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(20.dp),
                        modifier = modifier
                            .padding(top = 10.dp)
                            .fillMaxHeight()
                    ) {
                        items(uiState.data ?: emptyList()) { restaurant ->
                            restaurant.let { it1 ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp)
                                        .clip(RoundedCornerShape(65.dp))
                                        .background(MaterialTheme.colorScheme.onPrimary)
                                ) {
                                    RestaurantListItem(
                                        list = it1,
                                        modifier = modifier.animateItemPlacement(
                                            tween(
                                                durationMillis = 100
                                            )
                                        ),
                                        onItemClick = { itemId ->
                                            navController.navigate(
                                                Screen.Detail.createRoute(
                                                    itemId
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Toast.makeText(
                LocalContext.current, uiState.message.toString(), Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
    }
}


@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp), color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun RestaurantListItem(
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
                onItemClick(list.id.toString())
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = 70.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
        androidx.compose.material3.SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            placeholder = {
                Text(
                    stringResource(R.string.search_restaurant),
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            },
            shape = MaterialTheme.shapes.large,
        ) {}
    }
}
