package com.food.delivery.splash.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.food.delivery.R
import com.food.delivery.presentation.navigation.Routes
import com.food.delivery.presentation.navigation.SubNavigation
import com.food.delivery.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    var startAnimation by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.9f,
        animationSpec = tween(durationMillis = 1000),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == null) return@LaunchedEffect

        if (isLoggedIn == true) {
            navController.navigate(Routes.DeliveryScreen) {
                popUpTo(SubNavigation.SplashGraph) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            navController.navigate(Routes.LoginScreen) {
                popUpTo(SubNavigation.SplashGraph) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF7A00))
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Crop fills complete width and height
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )
    }
}