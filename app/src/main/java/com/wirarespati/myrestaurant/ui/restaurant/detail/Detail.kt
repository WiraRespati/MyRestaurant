package com.wirarespati.myrestaurant.ui.restaurant.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.wirarespati.myrestaurant.R
import com.wirarespati.myrestaurant.core.data.Resource
import com.wirarespati.myrestaurant.core.domain.model.DetailRestaurant
import com.wirarespati.myrestaurant.core.domain.model.DrinksItem
import com.wirarespati.myrestaurant.core.domain.model.FoodsItem
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val viewModel: DetailViewModel = get()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val restaurantId = navBackStackEntry?.arguments?.getString("itemId")
    if (restaurantId != null) {
        viewModel.getDetailRestaurant(restaurantId)
    }

    val uiState by viewModel.uiState.observeAsState()

    when (val result = uiState) {
        is Resource.Loading -> {
            LoadingItem()
        }

        is Resource.Success -> {
            val restaurant = result.data
            restaurant?.let {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Detail Restaurant", modifier = Modifier.padding(top = 4.dp)
                            )

                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),

                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                    )

                    val context = LocalContext.current
                    if (restaurantId != null) {
                        DetailContent(
                            detail = it, restaurantId = restaurantId, viewModel = viewModel, context = context
                        )
                    }
                }
            }
        }

        is Resource.Error -> {
            Toast.makeText(LocalContext.current, result.message, Toast.LENGTH_SHORT).show()
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
fun DetailContent(
    detail: DetailRestaurant,
    restaurantId: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    context: Context
) {


    LazyColumn(
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.onPrimary)

            .fillMaxSize()


    ) {
        item {
            AsyncImage(
                model = "https://restaurant-api.dicoding.dev/images/large/${detail.pictureId}",
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(250.dp)
            )
            Column {
                Row(modifier = modifier.align(CenterHorizontally)) {
                    Text(
                        text = detail.name.toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
                Row(
                    modifier = modifier.align(CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_city),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = detail.city.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                    )
                }

                Row(
                    modifier = modifier.align(CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = detail.address.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                    )
                }
                Row(
                    modifier = modifier.align(CenterHorizontally)
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
                        text = detail.rating.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                    )
                }

                Row(modifier = modifier.align(CenterHorizontally)) {
                    FavoriteIcon(restaurantId, viewModel, context)
                    Text(
                        text = "Add to Favorite",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = modifier.padding(top = 10.dp)
                    )
                }

            }

        }

        item {
            Text(
                text = "Foods",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            FoodItem(food = detail.menus?.foods)
        }
        item {
            Text(
                text = "Drinks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            DrinkItem(drink = detail.menus?.drinks)
        }
    }
}

@Composable
fun FoodItem(food: List<FoodsItem?>?, modifier: Modifier = Modifier) {

    LazyRow(

        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(food.orEmpty()) { foodItem ->
            NameFood(foodItem)
        }
    }
}

@Composable
fun DrinkItem(drink: List<DrinksItem?>?, modifier: Modifier = Modifier) {

    LazyRow(

        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(drink.orEmpty()) { drinkItem ->
            drinkItem?.let { NameDrink(it) }
        }
    }
}

@Composable
fun NameFood(food: FoodsItem?) {
    Text(
        text = food?.name ?: "",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .border(
                1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    )
}

@Composable
fun NameDrink(drink: DrinksItem, modifier: Modifier = Modifier) {
    Text(
        text = drink.name ?: "",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .border(
                1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    )
}

@Composable
fun FavoriteIcon(restaurantId: String, viewModel: DetailViewModel, context: Context) {
    LaunchedEffect(viewModel) {
        viewModel.isFavorite(restaurantId)
    }
      val isFavorite by viewModel.isFavorite.observeAsState()
    IconButton(
        onClick = {
            viewModel.setFavoriteRestaurant(restaurantId)
             val message = if (isFavorite == true) "Removed from favorites"  else "Added to favorites"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    ) {
        val icon = if (isFavorite == true) {
            Icons.Filled.Favorite
        } else {
            Icons.Default.FavoriteBorder
        }
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Red
        )
    }

}
