package com.food.delivery.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.food.delivery.R
import com.food.delivery.presentation.navigation.Routes
import com.food.delivery.presentation.navigation.SubNavigation
import com.food.delivery.splash.viewmodel.SplashViewModel



@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {

        when (isLoggedIn) {

            true -> {
                navController.navigate(SubNavigation.MainHomeScreen) {
                    popUpTo(SubNavigation.SplashGraph) {
                        inclusive = true
                    }
                }
            }

            false -> {
                navController.navigate(SubNavigation.LoginSignUpScreen) {
                    popUpTo(SubNavigation.SplashGraph) {
                        inclusive = true
                    }
                }
            }

            null -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}