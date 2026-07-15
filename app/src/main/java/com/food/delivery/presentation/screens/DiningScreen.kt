package com.food.delivery.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.food.delivery.R
import com.food.delivery.presentation.components.DiningScreenContent
import com.food.delivery.presentation.components.DiningSearchBar
import com.food.delivery.presentation.components.DiningSliderComponent
import com.food.delivery.presentation.components.RestaurantPromotion
import com.food.delivery.presentation.components.TopAppBarDiningScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiningScreen(navController: NavController, listState: LazyListState){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val samplePromotions = listOf(
        RestaurantPromotion(
            imageRes = R.drawable.restaurant1,
            name = "Lezzetli",
            tagline = "Experience the finer thing",
            discount = "Flat 15% OFF"
        ),
        RestaurantPromotion(
            imageRes = R.drawable.restaurant2,
            name = "Spice Garden",
            tagline = "Authentic flavors of India",
            discount = "Buy 1 Get 1 Free"
        ),
        RestaurantPromotion(
            imageRes = R.drawable.restaurant3,
            name = "Sushi Paradise",
            tagline = "Fresh from the ocean",
            discount = "20% OFF on weekdays"
        )
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBarDiningScreen(
                    scrollBehavior = scrollBehavior,
                    navController = navController
                )

                DiningSearchBar(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {

            item {
                AsyncImage(
                model = R.drawable.diningbanner,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                contentScale = ContentScale.Crop
            )
            }

            item {
                DiningSliderComponent(
                    promotions = samplePromotions,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                DiningScreenContent()
            }
        }
    }
}

//@Composable
//fun DiningScreenLazyColumn(samplePromotions: List<RestaurantPromotion>) {
//    Column {
//        Box {
//            AsyncImage(
//                model = R.drawable.diningbanner,
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(360.dp),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        DiningSliderComponent( promotions = samplePromotions, modifier = Modifier.padding(horizontal = 16.dp) )
//
//        DiningScreenContent()
//    }
//}