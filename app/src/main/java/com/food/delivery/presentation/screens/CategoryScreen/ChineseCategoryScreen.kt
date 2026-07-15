package com.food.delivery.presentation.screens.CategoryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.food.delivery.R
import com.food.delivery.presentation.components.FilterRow
import com.food.delivery.presentation.components.HomeScreenCards

@Composable
fun ChineseCategoryScreen(navController: NavController) {
    ChineseCategoryCards(navController)
}

@Composable
fun ChineseCategoryCards(navController: NavController) {
    val LazyListState = rememberLazyListState()
    val filters = listOf("Filter", "Flash Sale", "Under 30 mins", "Rating", "Schedule")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FilterRow(filters)

        Text(
            text = "RECOMMENDED FOR YOU",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 15.dp),
            textAlign = TextAlign.Center
        )


        CategoryCard(
            R.drawable.pizza_image,
            "Get item @₹50 only",
            "Dominos Pizza..",
            "30 mins",
            R.drawable.timer,
            R.drawable.pepperoni_pizza,
            "Get item @₹99 only",
            "La Pino'z Pizza",
            "15 mins",
                    R.drawable.timer,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column() {
                Text(
                    text = "660 RESTAURANTS DELIVERING TO YOU",
                    modifier = Modifier,
                    color = Color.Gray
                )
                Text(
                    text = "Featured",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(navController)
        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(navController)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun CategoryCard(
    foodimagebackground1: Int,
    textblackone1: String,
    cardtextname1: String,
    timingtext1: String,
    timerimage1 : Int,
    foodimagebacground2: Int,
    textblackone2: String,
    cardtextname2: String,
    timingtext2: String,
    timerimage2: Int
) {

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(650.dp).padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(20) { index ->
            Column(modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .background(Color.White)) {
                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(100.dp)
                        .padding(horizontal = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = foodimagebackground1),
                            contentDescription = "Burger",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = textblackone1,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(bottom = 8.dp)
                                .background(Color.Black.copy(alpha = 0.80f)),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            painter = painterResource(R.drawable.outline_bookmark_24),
                            contentDescription = "bookmark",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            tint = Color.White
                        )
                    }
                }
                Column {
                    Text(text = cardtextname1,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold)
                    Row(modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = timerimage1), contentDescription = "timer",
                            modifier = Modifier.size(14.dp), tint = colorResource(R.color.green)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = timingtext1,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Card(modifier = Modifier
                    .width(140.dp)
                    .height(100.dp)
                    .padding(horizontal = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = foodimagebacground2,
                            contentDescription = "Dining Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = textblackone2,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(bottom = 8.dp)
                                .background(Color.Black.copy(alpha = 0.80f)),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(painter = painterResource(R.drawable.outline_bookmark_24),
                            contentDescription = "bookmark",
                            modifier = Modifier.
                            size(30.dp).
                            align(Alignment.TopEnd).
                            padding(8.dp),
                            tint = Color.White
                        )
                    }
                }
                Column {
                    Text(text = cardtextname2,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(id = timerimage2),contentDescription = "timer",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Green)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = timingtext2,
                            fontSize = 12.sp,
                            color = Color.Gray)
                    }
                }
            }
        }
    }
}