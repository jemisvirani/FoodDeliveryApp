package com.food.delivery.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.food.delivery.presentation.components.DeliveryScreenSearchBar
import com.food.delivery.presentation.components.FoodCategoryTab
import com.food.delivery.presentation.components.TopAppBarDeliveryScreen
import com.food.delivery.presentation.screens.CategoryScreen.AllCategoryScreen
import com.food.delivery.presentation.screens.CategoryScreen.ChineseCategoryScreen
import com.food.delivery.presentation.screens.CategoryScreen.PizzaCategoryScreen
import kotlin.with

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DeliveryScreen(navController: NavController, listState: LazyListState) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                TopAppBarDeliveryScreen(scrollBehavior, navController)
                Spacer(modifier = Modifier.height(3.dp))
                DeliveryScreenSearchBar(navController)
            }
        }) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 0.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            stickyHeader {

                FoodCategoryTab(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
            }
            item {
                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        val isForward = targetState > initialState

                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { if (isForward) it else -it }
                        ) + fadeIn(
                            animationSpec = tween(300)
                        ) togetherWith
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { if (isForward) -it else it }
                                ) + fadeOut(
                            animationSpec = tween(300)
                        ) using SizeTransform(
                            clip = false
                        )
                    },
                    label = "CategoryAnimation"
                ){ index ->
                    when(index){
                        0 -> AllCategoryScreen(navController)
                        1 -> PizzaCategoryScreen(navController)
                        2 -> ChineseCategoryScreen(navController)
                    }
                }
            }
        }
    }
}
