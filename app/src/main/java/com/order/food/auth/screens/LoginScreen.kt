package com.order.food.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.order.food.R
import com.order.food.auth.ui.components.login.LoginAction
import com.order.food.auth.ui.components.login.LoginEvent
import com.order.food.auth.ui.components.login.LoginUiState
import com.order.food.auth.ui.components.signup.AuthButton
import com.order.food.auth.ui.components.signup.AuthHeader
import com.order.food.auth.ui.components.signup.SignUpAction
import com.order.food.auth.viewmodel.LoginViewModel


@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {

        viewModel.event.collect { event ->

            when (event) {

                LoginEvent.NavigateToHome ->
                    onLoginSuccess()

                LoginEvent.NavigateToForgotPassword ->
                    onForgotPasswordClick()

                LoginEvent.NavigateToSignUp ->
                    onSignUpClick()

                is LoginEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    LoginScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}


@Composable
fun LoginScreen(
    state: LoginUiState,
    snackbarHostState: SnackbarHostState,
    onAction: (LoginAction) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    val emailBring = remember { BringIntoViewRequester() }
    val passwordBring = remember { BringIntoViewRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = colorResource(R.color.orange),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically { -it / 4 }
            ) {
                AuthHeader(
                    title = "Welcome Back",
                    subtitle = "Login to continue."
                )
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.buttonColor)
                    )

                    Spacer(Modifier.height(20.dp))

                    AuthTextField(
                        value = state.email,
                        onValueChange = {
                            onAction(LoginAction.EmailChanged(it))
                        },
                        label = "Email",
                        leadingIcon = Icons.Outlined.Email,
                        error = state.emailError,
                        focusRequester = emailFocus,
                        bringIntoViewRequester = emailBring,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )

                    PasswordTextField(
                        value = state.password,
                        onValueChange = {
                            onAction(LoginAction.PasswordChanged(it))
                        },
                        passwordVisible = state.isPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(LoginAction.TogglePassword)
                        },
                        label = "Password",
                        leadingIcon = Icons.Outlined.Lock,
                        error = state.passwordError,
                        focusRequester = passwordFocus,
                        bringIntoViewRequester = passwordBring,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboard?.hide()
                                focusManager.clearFocus()
                                onAction(LoginAction.LoginClicked)
                            }
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = state.rememberMe,
                                onCheckedChange = {
                                    onAction(LoginAction.RememberMeChanged(it))
                                },
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Remember me",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        TextButton(
                            onClick = {
                                onAction(LoginAction.ForgotPasswordClicked)
                            },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Forgot Password?")
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    AuthButton(
                        text = "Login",
                        loading = state.isLoading,
                        enabled = state.isFormValid,
                        onClick = {
                            onAction(LoginAction.LoginClicked)
                        }
                    )


                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Don't have an account?"
                        )

                        TextButton(
                            onClick = {
                                onAction(LoginAction.SignUpClicked)
                            }
                        ) {
                            Text(
                                text = "Sign Up",
                                color = Color(0xFFFF6F00),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}