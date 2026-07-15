package com.food.delivery.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.food.delivery.R
import com.food.delivery.presentation.components.HomeScreenCards
import com.food.delivery.presentation.components.TopAppBarDiningScreen
import com.food.delivery.presentation.navigation.Routes
import com.food.delivery.presentation.utils.BottomSheetToAddProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickScreen(navController: NavController, listState: LazyListState) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBarDiningScreen(
                    scrollBehavior = scrollBehavior,
                    navController = navController
                )
            }
        ) { innerPadding ->

            val quickItems = remember { List(6) { it } }
            val homeItems = remember { List(20) { it } }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item(key = "banner") {
                    Image(
                        painter = painterResource(R.drawable.quickbanner),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            ),
                        contentScale = ContentScale.Crop
                    )
                }

                item(key = "categories") {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = quickItems,
                            key = { it }
                        ) {
                            QuickScreenComponent(
                                navController = navController,
                                onAddClick = {
                                    showBottomSheet = true
                                }
                            )
                        }
                    }
                }

                items(
                    items = homeItems,
                    key = { it }
                ) {
                    HomeScreenCards(
                       onClick = {
                           navController.navigate(Routes.ParticularCardScreen("quick"))
                       }
                    )
                }
            }
        }

        if (showBottomSheet) {
            Dialog(
                onDismissRequest = {
                    showBottomSheet = false
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = false,
                    dismissOnBackPress = true,
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        IconButton(
                            onClick = {
                                showBottomSheet = false
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.75f),
                            shape = RoundedCornerShape(
                                topStart = 24.dp,
                                topEnd = 24.dp
                            )
                        ) {

                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                BottomSheetToAddProduct(
                                    navController = navController,
                                    onClose = {
                                        showBottomSheet = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun QuickScreenComponent(navController: NavController, onAddClick: () -> Unit) {

    var productName by remember { mutableStateOf("Peri Peri Burger") }
    var rating by remember { mutableStateOf("4.2") }
    var price by remember { mutableStateOf("₹249") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(180.dp)) {
                Row() {
                    Icon(
                        painter = painterResource(R.drawable.veg_icon),
                        modifier = Modifier.size(17.dp),
                        tint = colorResource(R.color.green),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Card(
                        modifier = Modifier.padding(start = 8.dp), shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.orange).copy(
                                alpha = 0.1f
                            )
                        )
                    ) {
                        Text(
                            text = "Bestseller",
                            modifier = Modifier.padding(horizontal = 3.dp),
                            colorResource(R.color.orange), fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = productName,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.greyishBlack),
                    modifier = Modifier.padding(horizontal = 1.dp, vertical = 8.dp),
                    fontSize = 18.sp
                )

                Card(modifier = Modifier.size(width = 45.dp, height = 22.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.ViewActivityClickableColor))) {

                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = rating, modifier = Modifier.padding(start = 4.dp),
                            fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        androidx.compose.material3.Icon(
                            painterResource(R.drawable.star),
                            modifier = Modifier.padding(start = 5.dp, top = 6.dp, bottom = 2.dp)
                                .size(10.dp),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }

                Text(
                    text = price,
                    modifier = Modifier.padding(horizontal = 1.dp, vertical = 8.dp),
                    color = colorResource(R.color.greyishBlack),
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.burger3),
                    modifier = Modifier
                        .size(130.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    contentDescription = "Product Image",
                    alignment = Alignment.Center
                )
                Card(
                    onClick = {
                        onAddClick()
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 120.dp)
                        .size(width = 100.dp, height = 36.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.lightPink)),
                    border = BorderStroke(width = 1.dp, color = colorResource(R.color.addButtonRed))
                ) {

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "ADD",
                            modifier = Modifier.padding(start = 30.dp, top = 6.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(R.color.addButtonRed),
                            fontSize = 20.sp
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_24),
                            contentDescription = "Add item",
                            tint = Color.Red,
                            modifier = Modifier
                                .padding(3.dp)
                                .size(14.dp)
                        )
                    }
                }
            }
        }
    }


}