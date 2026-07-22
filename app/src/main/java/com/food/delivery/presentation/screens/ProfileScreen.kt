package com.food.delivery.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.food.delivery.R;
import com.food.delivery.presentation.navigation.Routes
import com.food.delivery.presentation.navigation.SubNavigation
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                modifier = Modifier.background(color = colorResource(R.color.lightOrange)),
                title = {},
                navigationIcon = {
                    Box(Modifier.fillMaxWidth()) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Arrowback",
                            )
                        }
                        Text(text = "My Profile", color = Color.Black, modifier = Modifier.align(Alignment.Center))
                    }

                }
            )
        }) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.gray))
                .padding(values)
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.profielogo),
                        contentDescription = "Profile Image",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Column {
                        Text(
                            text = "Name",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                        Text(
                            text = "gmail2026@gmail.com",
                            color = Color.DarkGray,
                            fontSize = 12.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "View activity",
                                fontSize = 13.sp,
                                color = colorResource(id = R.color.addButtonRed)
                            )
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                tint = Color.DarkGray,
                                contentDescription = "Forward Arrow",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            ZomatoGold()

            LazyColumns(navController)
        }

    }
}

@Composable
fun ZomatoGold() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(65.dp),
        shape = RoundedCornerShape(
            bottomEnd = 12.dp,
            bottomStart = 12.dp,
            topStart = 0.dp,
            topEnd = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.goldicon1),
                contentDescription = "Zomato gold Image",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Join Zomato Gold",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow right",
                tint = Color.White
            )

        }
    }
}

@Composable
fun LazyColumns(navController: NavController) {

    var showLogoutDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = {
                showLogoutDialog = false
            },
            onLogout = {

                showLogoutDialog = false

                FirebaseAuth.getInstance().signOut()

                navController.navigate(SubNavigation.SplashGraph) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }


    val cardList = listOf(
        CardItem.ColumnGrid(name = "Ayush"),
        CardItem.FirstCard(profileName = "Your Profile", percentageText = "48%Completed"),
        CardItem.TwelfthCard(title = "Change Password"),
        CardItem.SecondCard(vegText = "Veg Mode"),
        CardItem.ThirdCard(appearance = "Appearance"),
        CardItem.FourthCard(rating = "Your rating"),
        CardItem.FifthCard(foodOrder = "Food Orders"),
        CardItem.SixthCard(titleName = "Dining and Experience"),
        CardItem.SeventhCard(title = "done"),
        CardItem.EighthCard(title = "done"),
        CardItem.NinthCard(title = "done"),
        CardItem.TenthCard(title = "done"),
        CardItem.EleventhsCard(title = "eleventhdone")
    )

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(cardList){ item ->
            when(item){
                is CardItem.ColumnGrid -> ColumGrid(item)
                is CardItem.FirstCard -> ProfileCard(item)
                is CardItem.TwelfthCard -> TwelfthCard(item,navController)
                is CardItem.SecondCard -> VegModeCard(item)
                is CardItem.ThirdCard -> AppearanceCard(item)
                is CardItem.FourthCard -> RatingCard(item)
                is CardItem.FifthCard -> FoodOrderCard(item)
                is CardItem.SixthCard -> DiningCard(item)
                is CardItem.SeventhCard -> SeventhCard(item)
                is CardItem.EighthCard -> EighthCard(item)
                is CardItem.NinthCard -> NinthCard(item)
                is CardItem.TenthCard -> TenthCard(item)
                is CardItem.EleventhsCard -> EleventhCard(item,navController, onShowLogoutDialog = {
                    showLogoutDialog = it
                })
            }
        }
    }


}

@Composable
fun TwelfthCard(x0: CardItem.TwelfthCard, navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp).clickable{
        navController.navigate(Routes.ChangePasswordScreen)
    },
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Password,
                contentDescription = "Your Profile Icon",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Change Password", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "forward arrow",
                modifier = Modifier.padding(start = 5.dp),
                tint = Color.DarkGray)
        }
    }
}

@Composable
fun ProfileCard(card: CardItem.FirstCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.AccountCircle,
                contentDescription = "Your Profile Icon",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Your Profile", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "48% Completed",
                modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)).background(color = colorResource(R.color.cream)).padding(8.dp),
                color = colorResource(R.color.green), fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "forward arrow",
                modifier = Modifier.padding(start = 5.dp),
                tint = Color.DarkGray)
        }
    }
}

@Composable
fun NinthCard(card: CardItem.NinthCard) {
    Card(modifier = Modifier.padding(start = 10.dp, top = 13.dp, end = 10.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Zomato For Enterprise",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.building),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "For employs", color = Color.Black,
                modifier = Modifier.padding(start= 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.empcard),
                contentDescription = "Your orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "For employs", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun EighthCard(card: CardItem.EighthCard) {
    Card(modifier = Modifier.padding(start = 10.dp, top = 13.dp, end = 10.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Money",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.rupeesymbol),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)

            Text(text = "Zomato Money", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.giftcard),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Buy Gift Card", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.claimgiftcard),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Claim Gift Card", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.z),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Zomato Credits", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                modifier = Modifier.padding(start = 5.dp, top = 10.dp),
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.payment),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Payment Setting", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                modifier = Modifier.padding(start = 5.dp, top = 10.dp),
                tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun SeventhCard(card: CardItem.SeventhCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Feeding India",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.impact),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Your impact", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.reciept),
                contentDescription = "Your orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Get Feeding India receipt", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun DiningCard(x0: CardItem.SixthCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Dining and Experiences ",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.hiddden),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Your dining transactions", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.gift),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Your dining rewards", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.dining),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Your bookings", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.message),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Dining help", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Settings,
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Dining settings", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Info,
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Frequently asked questions", modifier = Modifier.padding(start = 10.dp), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun FoodOrderCard(card: CardItem.FifthCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Food orders",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.order),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Your orders", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Favourite orders", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.thumbs),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Manage recommendations", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.thumbs),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Order on train", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.addressbook),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Address book", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.message),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Online ordering help", color = Color.Black, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun RatingCard(card: CardItem.FourthCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.rating),
                contentDescription = "Rating Icon",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Your Rating", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(R.drawable.startrating),
                contentDescription = "star rating",
                tint = Color.Unspecified)
        }
    }
}

@Composable
fun AppearanceCard(card: CardItem.ThirdCard) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.paint),
                contentDescription = "Your Profile",
                modifier = Modifier.size(20.dp),
                tint = Color.LightGray)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Appearance", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "LIGHT",
                fontWeight = FontWeight.Bold,
                color = Color.Gray)
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }
    }
}

@Composable
fun VegModeCard(card: CardItem.SecondCard) {
    var switch1 by remember { mutableStateOf(true) }
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.veg_icon),
                contentDescription = "Veg Mode Icon",
                modifier = Modifier.size(22.dp),
                tint = colorResource(R.color.green)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Veg Mode", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = switch1,
                onCheckedChange = {switch1 = it},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Red,
                    checkedTrackColor = colorResource(R.color.light_green_track),
                    uncheckedTrackColor = colorResource(R.color.light_red_track),
                    checkedBorderColor = colorResource(R.color.black),
                    uncheckedBorderColor = colorResource(R.color.black)
                ),
                modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TenthCard(card: CardItem.TenthCard) {
    Card(modifier = Modifier.padding(start = 10.dp, top = 13.dp, end = 10.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "Coupons",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.coupons),
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)

            Text(text = "Collected coupons", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.coupons),
                contentDescription = "Your orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Redeem Gold coupons", color = Color.Black,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun EleventhCard(
    card: CardItem.EleventhsCard,
    navController: NavController,
    onShowLogoutDialog: (Boolean) -> Unit
) {


    Card(modifier = Modifier.padding(start = 10.dp, top = 13.dp, end = 10.dp, bottom = 13.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column {
            Row {
                VerticalDivider(
                    thickness = 3.dp,
                    modifier = Modifier.height(34.dp).padding(top = 12.dp).clip(shape = RoundedCornerShape(10.dp)),
                    color = colorResource(R.color.black)
                )
                Text(text = "More",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Info,
                contentDescription = "Your Orders",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "About", color = Color.Black,
                modifier = Modifier.padding(start =  10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.feedback),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Send feedback", color = Color.Black,
                modifier = Modifier.padding(start =  10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }
         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Info,
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Report a safety emergency", color = Color.Black,
                modifier = Modifier.padding(start =  10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Settings,
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Settings", color = Color.Black,
                modifier = Modifier.padding(start =  10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

         HorizontalDivider(thickness = 0.5.dp,
            modifier = Modifier.padding(start = 53.dp, top = 10.dp).weight(0.2f),
            color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 4.dp).clickable{
            onShowLogoutDialog(true)
        },
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.logout),
                contentDescription = "Your Profile",
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray)
            Text(text = "Logout", color = Color.Black,
                modifier = Modifier.padding(start =  10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "forward arrow",
                tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun ColumGrid(card: CardItem.ColumnGrid) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(R.drawable.outline_bookmark_24),
                    contentDescription = "Collection",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 15.dp, bottom = 5.dp)
                        .size(30.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Collection",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(R.drawable.rupeesymbol),
                    contentDescription = "Money",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 15.dp, bottom = 5.dp)
                        .size(30.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Money",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier
                            .size(width = 30.dp, height = 20.dp)
                            .background(color = colorResource(R.color.green)),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹0",
                            color = colorResource(R.color.white),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

sealed class CardItem {
    data class FirstCard(
        val profileName: String,
        val percentageText: String
    ) : CardItem()

    data class ColumnGrid(val name: String)
    data class SecondCard(val vegText: String) : CardItem()
    data class ThirdCard(val appearance: String) : CardItem()
    data class FourthCard(val rating: String)
    data class FifthCard(val foodOrder: String)
    data class SixthCard(val titleName: String)
    data class SeventhCard(val title: String)
    data class EighthCard(val title: String)
    data class NinthCard(val title: String)
    data class TenthCard(val title: String)
    data class EleventhsCard(val title: String)
    data class TwelfthCard(val title: String)
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onLogout: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Logout", color = Color.Black)
        },
        text = {
            Text("Are you sure you want to logout?", color = Color.Black)
        },
        confirmButton = {
            TextButton(onClick = onLogout) {
                Text("Logout", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Black)
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewProfile() {
//    ProfileScreen(rememberNavController())
//}