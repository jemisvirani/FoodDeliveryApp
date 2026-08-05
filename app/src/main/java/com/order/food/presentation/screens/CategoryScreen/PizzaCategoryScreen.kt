package com.order.food.presentation.screens.CategoryScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PizzaCategoryScreen(navController: NavController){
    ChineseCategoryCards(navController = navController)
}