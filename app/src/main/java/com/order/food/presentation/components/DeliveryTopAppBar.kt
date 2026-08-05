package com.order.food.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.order.food.R
import com.order.food.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDeliveryScreen(scrollBehavior: TopAppBarScrollBehavior,navController: NavController){
    TopAppBar(title = {
        Column{
            Row(modifier = Modifier.fillMaxWidth().height(22.dp)) {
                Text(text = "Home", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                Icon(painter = painterResource(R.drawable.down_arrow),
                    modifier = Modifier.size(30.dp).padding(top = 6.dp),
                    tint = Color.DarkGray,
                    contentDescription = "Down Arrow"
                )
            }
            Text(text = "101,Ram Nagar,Kapodra,Surat",
                fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)

        }
    },
     navigationIcon = {
         Icon(painter = painterResource(R.drawable.locationdiningscreen),
             modifier = Modifier.size(35.dp),
             tint = colorResource(R.color.buttonColor),
             contentDescription = "Location")
     },
        actions = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = colorResource(R.color.buttonColor),
                        shape = CircleShape
                    )
                    .clickable {
                        navController.navigate(Routes.ProfileScreen)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "R",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        scrollBehavior = scrollBehavior,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}