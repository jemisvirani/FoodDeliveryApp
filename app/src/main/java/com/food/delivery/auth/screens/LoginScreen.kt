package com.food.delivery.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.food.delivery.auth.viewmodel.LoginViewModel
import com.food.delivery.presentation.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeVisible = WindowInsets.isImeVisible

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(state.snackBar) {
        state.snackBar?.let { message ->
            snackBarHostState.showSnackbar(message)
            viewModel.clearSnackBar()
        }
    }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LoginHeader(
                imeVisible = imeVisible,
                onBackClick = { navController.popBackStack() })

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (imeVisible) 8.dp else 235.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                val emailFocus = remember { FocusRequester() }
                val passwordFocus = remember { FocusRequester() }
                val buttonFocus = remember { FocusRequester() }

                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()

                val emailBring = remember { BringIntoViewRequester() }
                val passwordBring = remember { BringIntoViewRequester() }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {

                    LazyColumn(
                        state = listState,
                    ) {

                        item {
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
                                focusRequester = emailFocus,
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next,
                                isError = state.emailError != null,
                                errorMessage = state.emailError,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        scope.launch {
                                            listState.animateScrollToItem(1)
                                            passwordFocus.requestFocus()
                                        }
                                    }
                                )
                            )
                        }


                        item {
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
                                focusRequester = passwordFocus,
                                imeAction = ImeAction.Done,
                                isError = state.passwordError != null,
                                errorMessage = state.passwordError,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                )
                            )
                        }
                    }


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
                                scope.launch {
                                    focusManager.clearFocus(force = true)
                                    keyboardController?.hide()

                                    viewModel.login {
                                        navController.navigate(Routes.DeliveryScreen) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = false
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .focusRequester(buttonFocus)
                            .focusable()
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
}

@Composable
fun LoginHeader(imeVisible: Boolean, onBackClick: () -> Boolean) {
    AnimatedVisibility(
        visible = !imeVisible, enter = fadeIn(), exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Color(0xFF121223))
        ) {

            Spacer(modifier = Modifier.height(45.dp))

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White), contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Log In",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please sign in to your existing account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
        }
    }
}