package com.order.food.presentation.screens.CategoryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.order.food.presentation.components.ExploreRow
import com.order.food.presentation.components.HomeScreenCards
import com.order.food.presentation.navigation.Routes

@Composable
fun AllCategoryScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        ExploreRow()
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp),
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

        HomeScreenCards(
            onClick = {
                navController.navigate(Routes.ParticularCardScreen("delivery"))

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(
            onClick = {
                navController.navigate(Routes.ParticularCardScreen("delivery"))

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(
            onClick = {
                navController.navigate(Routes.ParticularCardScreen("delivery"))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeScreenCards(
            onClick = {
                navController.navigate(Routes.ParticularCardScreen("delivery"))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))

    }
}