package com.order.food.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.order.food.R
import com.order.food.cart.model.Product
import com.order.food.cart.viewmodel.CartViewModel
import com.order.food.presentation.utils.BottomSheetToAddProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticularCardScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Hidden
        }        )

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()


    var firstName by remember { mutableStateOf("Rominus Pizza") }
    var lastName by remember { mutableStateOf("Burger") }
    var rating by remember { mutableStateOf("4.2") }
    var time by remember { mutableStateOf("37 mins") }
    var distance by remember { mutableStateOf("2.6 km") }
    var address by remember { mutableStateOf("Crossing Republic") }
    var uniqueness by remember { mutableStateOf("On time preparation") }
    var discount by remember { mutableStateOf("Flat Rs50 OFF on above Rs249") }
    var offers by remember { mutableStateOf("2 offers") }
    var price by remember { mutableStateOf("₹249") }
    var productName by remember { mutableStateOf("Peri Peri Burger") }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val products = remember {
        listOf(
            Product(
                id = 1,
                name = "Peri Peri Burger",
                price = 249.0,
                rating = "3.2",
                image = "",
                description = "Burger"
            ),
            Product(
                id = 2,
                name = "Cheese Burger",
                price = 199.0,
                rating = "4.2",
                image = "",
                description = "Burger"
            ),
            Product(
                id = 3,
                name = "Veg Burger",
                price = 149.0,
                rating = "4.5",
                image = "",
                description = "Burger"
            )
        )
    }

    val cartViewModel: CartViewModel = viewModel()

    val cartItems by cartViewModel.cartItems.collectAsState()

    val totalItems = cartItems.sumOf { it.quantity }
    val totalAmount = cartItems.sumOf {
        it.product.price * it.quantity
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrowback),
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Back navigation"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.outline_group_add_24),
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp),
                            contentDescription = "Group Add"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.outline_bookmark_24),
                            modifier = Modifier.size(28.dp),
                            tint = Color.Black,
                            contentDescription = "Save or favourite"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.outline_more_vert_24),
                            modifier = Modifier.size(28.dp),
                            tint = Color.Black,
                            contentDescription = "More menu"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                ViewCartBar(
                    totalItems = totalItems,
                    totalAmount = totalAmount,
                    onClick = {
//                        navController.navigate("cart")
                    }
                )
            }
        }


    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(innerPadding)
        ) {

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .height(165.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(
                                text = firstName,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = lastName,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    painter = painterResource(R.drawable.outline_info_24),
                                    tint = Color.Gray,
                                    contentDescription = "Information"
                                )
                            }
                        }
                        Column {
                            Card(
                                modifier = Modifier.size(width = 55.dp, height = 30.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.ViewActivityClickableColor))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = rating,
                                        modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Icon(
                                        painterResource(R.drawable.star),
                                        modifier = Modifier
                                            .padding(
                                                start = 5.dp,
                                                top = 2.dp,
                                                end = 4.dp
                                            )
                                            .size(18.dp),
                                        tint = Color.White,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.timer),
                            tint = Color.Green,
                            modifier = Modifier.size(17.dp),
                            contentDescription = "timer Clock"
                        )
                        Text(
                            text = time, fontSize = 14.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.dot),
                            tint = Color.DarkGray,
                            modifier = Modifier.size(16.dp),
                            contentDescription = "dot"
                        )
                        Text(text = distance, fontSize = 14.sp, color = Color.DarkGray)
                        Icon(
                            painter = painterResource(R.drawable.dot),
                            tint = Color.DarkGray,
                            modifier = Modifier.size(16.dp),
                            contentDescription = "dot"
                        )
                        Text(text = address, fontSize = 14.sp, color = Color.DarkGray)
                        Icon(
                            painter = painterResource(R.drawable.down_arrow),
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .size(17.dp),
                            contentDescription = "down arrow"
                        )
                    }
                    Card(
                        modifier = Modifier.padding(start = 15.dp, 10.dp).width(165.dp)
                            .height(22.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(Color.Gray.copy(alpha = 0.2f)),
                    ) {
                        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painterResource(R.drawable.check),
                                modifier = Modifier.padding(start = 10.dp).size(12.dp),
                                tint = colorResource(R.color.green),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = uniqueness, color = Color.Gray, fontSize = 12.sp)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.discount),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(16.dp),
                            tint = Color.Blue,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = discount,
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = offers,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                        Icon(
                            painter = painterResource(R.drawable.down_arrow),
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(start = 4.dp, end = 8.dp)
                                .size(16.dp),
                            contentDescription = "down arrow"
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 14.dp),
                        thickness = 1.dp,
                        color = colorResource(R.color.gray)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Recommended for you",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painterResource(R.drawable.arrowup),
                            tint = Color.DarkGray,
                            modifier = Modifier.size(16.dp),
                            contentDescription = null
                        )
                    }

                    products.forEach { product ->

                        val quantity = cartItems
                            .find { it.product.id == product.id }
                            ?.quantity ?: 0

                        ItemsList(
                            product = product,
                            quantity = quantity,
                            onAddClick = { cartViewModel.addToCart(product) },
                            onIncrease = {
                                cartViewModel.increaseQuantity(product.id)
                            },
                            onDecrease = {
                                cartViewModel.decreaseQuantity(product.id)
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 14.dp),
                            thickness = 1.dp,
                            color = colorResource(R.color.gray)
                        )
                    }
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
                            Icon(
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
fun ItemsList(
    product: Product,
    quantity: Int,
    onAddClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.width(200.dp)) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.veg_icon),
                    modifier = Modifier.size(17.dp),
                    tint = colorResource(R.color.ViewActivityClickableColor),
                    contentDescription = null
                )

                Card(
                    modifier = Modifier.padding(start = 8.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.orange).copy(
                            alpha = 0.1f
                        )
                    )
                ) {
                    Text(
                        text = "Bestseller",
                        modifier = Modifier.padding(horizontal = 3.dp),
                        color = colorResource(R.color.greyishBlack), fontSize = 11.sp
                    )
                }
            }
            Text(
                text = product.name,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.greyishBlack),
                modifier = Modifier.padding(horizontal = 1.dp, vertical = 8.dp),
                fontSize = 18.sp
            )

            Card(
                modifier = Modifier.size(width = 50.dp, height = 22.dp),
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.ViewActivityClickableColor))
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = product.rating,
                        modifier = Modifier.padding(start = 3.dp, top = 2.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Icon(
                        painterResource(R.drawable.star),
                        modifier = Modifier
                            .padding(
                                start = 3.dp,
                                top = 2.dp,
                                end = 3.dp
                            )
                            .size(12.dp),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }

            Text(
                text = product.price.toString(),
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
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 120.dp)
                    .size(width = 100.dp, height = 36.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(colorResource(R.color.lightOrange)),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.addButtonRed)
                )
            ) {

                AnimatedContent(
                    targetState = quantity,
                    label = "Cart Animation"
                ) { count ->

                    if (count == 0) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onAddClick()
                                }
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = "Add item",
                                tint = colorResource(R.color.buttonColor),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 4.dp)
                                    .size(14.dp)
                            )

                            Text(
                                text = "ADD",
                                modifier = Modifier.align(Alignment.Center),
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(R.color.addButtonRed),
                                fontSize = 20.sp
                            )
                        }

                    } else {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .clickable { onDecrease() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = colorResource(R.color.addButtonRed),
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Text(
                                text = count.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.greyishBlack),
                                textAlign = TextAlign.Center
                            )

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .clickable { onIncrease() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = colorResource(R.color.addButtonRed),
                                    modifier = Modifier.size(16.dp)
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
fun ViewCartBar(
    totalItems: Int,
    totalAmount: Double,
    onClick: () -> Unit
) {

    AnimatedVisibility(
        visible = totalItems > 0,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.buttonColor)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "$totalItems Item${if (totalItems > 1) "s" else ""}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "₹${String.format("%.2f", totalAmount)}",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "View Cart",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}