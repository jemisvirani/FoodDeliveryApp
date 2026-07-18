package com.food.delivery.auth.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.food.delivery.auth.viewmodel.LoginViewModel
import com.food.delivery.presentation.navigation.Routes

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Color(0xFF121223))
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Log In",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please sign in to your existing account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 16.sp
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 220.dp),
            shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {

                Text(
                    text = "EMAIL",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                AppTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "example@gmail.com",
                    keyboardType = KeyboardType.Email,
                    isError = state.emailError != null,
                    errorMessage = state.emailError
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "PASSWORD",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "Enter your password",
                    visible = passwordVisible,
                    onVisibleChange = {
                        passwordVisible = !passwordVisible
                    },
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = state.rememberMe,
                            onCheckedChange = viewModel::onRememberMeChange
                        )

                        Text("Remember me")
                    }

                    Text(
                        text = "Forgot Password",
                        color = Color(0xFFFF7622),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onForgotPasswordClick()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        if (!state.isLoading) {
                            viewModel.login {
                                navController.navigate(Routes.DeliveryScreen) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF7622),
                        disabledContainerColor = Color(0xFFFF7622)
                    )
                ) {

                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "LOG IN",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Don't have an account? ",
                        color = Color.Gray
                    )

                    Text(
                        text = "SIGN UP",
                        color = Color(0xFFFF7622),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onSignUpClick()
                        }
                    )
                }
            }
        }
    }
}