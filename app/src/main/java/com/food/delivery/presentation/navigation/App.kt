package com.food.delivery.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bottombar.AnimatedBottomBar
import com.food.delivery.R
import com.food.delivery.auth.screens.ChangePasswordScreen
import com.food.delivery.auth.screens.ForgotPasswordScreen
import com.food.delivery.auth.screens.LoginScreen
import com.food.delivery.auth.screens.SignUpScreen
import com.food.delivery.presentation.screens.DeliveryScreen
import com.food.delivery.presentation.screens.DiningScreen
import com.food.delivery.presentation.screens.FinalCheckOutScreen
import com.food.delivery.presentation.screens.ParticularCardScreen
import com.food.delivery.presentation.screens.ProfileScreen
import com.food.delivery.presentation.screens.QuickScreen
import com.food.delivery.presentation.screens.SearchBarScreen
import okhttp3.Route

data class BottomNavItem(
    val title: String,
    val icon: Painter
)


@Composable
fun App(
    isVisible: Boolean,
    listState: LazyListState,

    ) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    val showBottomBar = lazyListState.isScrollingUp()
    var bottomBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {

        var previousIndex = 0
        var previousOffset = 0
        val threshold = 20

        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->

            if (!listState.isScrollInProgress) return@collect

            when {
                index > previousIndex -> bottomBarVisible = false
                index < previousIndex -> bottomBarVisible = true
                offset - previousOffset > threshold -> bottomBarVisible = false
                previousOffset - offset > threshold -> bottomBarVisible = true
            }

            previousIndex = index
            previousOffset = offset
        }
    }

    val showBottomBar = currentDestination?.let {
        it.hasRoute<Routes.DeliveryScreen>() ||
                it.hasRoute<Routes.QuickScreen>() ||
                it.hasRoute<Routes.DiningScreen>()
    } == true


    val selectedItemIndex = when {
        currentDestination?.hasRoute<Routes.DeliveryScreen>() == true -> 0
        currentDestination?.hasRoute<Routes.QuickScreen>() == true -> 1
        currentDestination?.hasRoute<Routes.DiningScreen>() == true -> 2

        currentDestination?.hasRoute<Routes.ParticularCardScreen>() == true -> {
            when (navBackStackEntry!!.toRoute<Routes.ParticularCardScreen>().source) {
                "delivery" -> 0
                "quick" -> 1
                "dining" -> 2
                else -> -1
            }
        }

        else -> -1
    }


    val BottomNavItems = listOf(
        BottomNavItem(
            title = "Delivery",
            icon = painterResource(R.drawable.delivery_cart)
        ),
        BottomNavItem(
            title = "Quick",
            icon = painterResource(R.drawable.quick_icon)
        ),
        BottomNavItem(
            title = "Dining",
            icon = painterResource(R.drawable.dining)
        ),
    )
    val selectedColor = colorResource(R.color.buttonColor)
    val bottomBarHeight by animateDpAsState(
        targetValue = if (isVisible) 64.dp else 0.dp
    )

    var startScreen = if (true) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                modifier = Modifier
                    .padding(
                        WindowInsets.navigationBars
                            .only(WindowInsetsSides.Bottom)
                            .asPaddingValues()
                    )
                    .fillMaxWidth(),
                visible = showBottomBar && bottomBarVisible,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color.White)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.LightGray.copy(alpha = 0.2f))
                    ) {
                        BottomNavItems.forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(1f)
                                    .height(3.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(
                                        if (index == selectedItemIndex)
                                            selectedColor
                                        else
                                            Color.Transparent
                                    )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shadowElevation = 8.dp
                    ) {
                        AnimatedBottomBar(
                            containerColor = Color.White,
                            animationSpec = spring(
                                dampingRatio = 1f,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ) {
                            BottomNavItems.forEachIndexed { index, item ->

                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        if (selectedItemIndex == index) return@NavigationBarItem

                                        val route = when (index) {
                                            0 -> Routes.DeliveryScreen
                                            1 -> Routes.QuickScreen
                                            2 -> Routes.DiningScreen
                                            else -> Routes.DeliveryScreen
                                        }

                                        navController.navigate(route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = item.title,
                                            color = if (index == selectedItemIndex)
                                                selectedColor
                                            else
                                                Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            painter = item.icon,
                                            contentDescription = item.title,
                                            modifier = Modifier.size(24.dp),
                                            tint = if (index == selectedItemIndex)
                                                selectedColor
                                            else
                                                Color.Gray
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            NavHost(navController = navController, startDestination = startScreen) {
                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreen(onSignUpClick = {
                            navController.navigate(Routes.SignUpScreen)
                        }, onForgotPasswordClick = {
                            navController.navigate(Routes.ForgetPasswordScreen)
                        })
                    }
                    composable<Routes.SignUpScreen> {
                        SignUpScreen(
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onLoginClick = {
                                navController.navigate(Routes.LoginScreen)
                            }
                        )
                    }
                    composable<Routes.ForgetPasswordScreen> {
                        ForgotPasswordScreen(onSuccess = {
                            navController.navigate(Routes.ChangePasswordScreen)
                        }, onBackClick = {
                            navController.navigate(Routes.LoginScreen)
                        })
                    }
                    composable<Routes.ChangePasswordScreen> {
                        ChangePasswordScreen(onBackClick = {
                            navController.navigate(Routes.ForgetPasswordScreen)
                        }, onPasswordChanged = {
                            navController.navigate(Routes.LoginScreen)
                        })
                    }
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.DeliveryScreen) {
                    composable<Routes.DeliveryScreen> {
                        DeliveryScreen(navController, listState)
                    }
                    composable<Routes.QuickScreen> {
                        QuickScreen(navController, listState)
                    }
                    composable<Routes.DiningScreen> {
                        DiningScreen(navController, listState)
                    }
                    composable<Routes.ProfileScreen> {
                        ProfileScreen(navController)
                    }
                    composable<Routes.ParticularCardScreen> {
                        ParticularCardScreen(navController)
                    }
                    composable<Routes.FinalCheckoutScreen> {
                        FinalCheckOutScreen(navController)
                    }
                    composable<Routes.SearchBarScreen> {
                        SearchBarScreen(modifier = Modifier, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember { mutableStateOf(firstVisibleItemIndex) }
    var previousOffset by remember { mutableStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun rememberBottomBarVisibility(
    listState: LazyListState
): Boolean {

    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        var previousIndex = listState.firstVisibleItemIndex
        var previousOffset = listState.firstVisibleItemScrollOffset

        snapshotFlow {
            listState.firstVisibleItemIndex to
                    listState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->

            if (index > previousIndex ||
                (index == previousIndex && offset > previousOffset)
            ) {
                isVisible = false
            } else if (index < previousIndex ||
                (offset < previousOffset)
            ) {
                isVisible = true
            }

            previousIndex = index
            previousOffset = offset
        }
    }

    return isVisible
}