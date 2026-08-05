package com.order.food.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.order.food.auth.ui.components.changepass.ChangePasswordAction
import com.order.food.auth.ui.components.changepass.ChangePasswordEvent
import com.order.food.auth.ui.components.changepass.ChangePasswordUiState
import com.order.food.auth.ui.components.signup.AuthButton
import com.order.food.auth.ui.components.signup.AuthHeader
import com.order.food.auth.ui.components.signup.PasswordStrengthIndicator
import com.order.food.auth.ui.components.signup.SignUpAction
import com.order.food.auth.viewmodel.ChangePasswordViewModel


@Composable
fun ChangePasswordRoute(
    onNavigateToLogin: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {

        viewModel.event.collect { event ->

            when (event) {

                ChangePasswordEvent.NavigateToLogin -> {
                    onNavigateToLogin()
                }

                is ChangePasswordEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    ChangePasswordScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChangePasswordScreen(
    state: ChangePasswordUiState,
    snackbarHostState: SnackbarHostState,
    onAction: (ChangePasswordAction) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val passwordFocus = remember { FocusRequester() }
    val confirmFocus = remember { FocusRequester() }

    val passwordBring = remember { BringIntoViewRequester() }
    val confirmBring = remember { BringIntoViewRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.orange),
        contentWindowInsets = WindowInsets.safeDrawing,
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
                    title = "Change Password",
                    subtitle = "Create a new secure password."
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "New Password",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.buttonColor)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PasswordTextField(
                        value = state.password,
                        onValueChange = {
                            onAction(ChangePasswordAction.PasswordChanged(it))
                        },
                        passwordVisible = state.isPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(ChangePasswordAction.TogglePassword)
                        },
                        label = "Password",
                        leadingIcon = Icons.Outlined.Lock,
                        error = state.passwordError,
                        focusRequester = passwordFocus,
                        bringIntoViewRequester = passwordBring,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )

                    AnimatedVisibility(
                        visible = state.password.isNotBlank(),
                        enter = fadeIn() +
                                slideInVertically { it / 2 } +
                                expandVertically(),
                        exit = fadeOut() +
                                slideOutVertically() +
                                shrinkVertically()
                    ) {

                        PasswordStrengthIndicator(
                            strength = state.passwordStrength
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    PasswordTextField(
                        value = state.confirmPassword,
                        onValueChange = {
                            onAction(ChangePasswordAction.ConfirmPasswordChanged(it))
                        },
                        passwordVisible = state.isConfirmPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(ChangePasswordAction.ToggleConfirmPassword)
                        },
                        label = "Confirm Password",
                        leadingIcon = Icons.Outlined.Lock,
                        error = state.confirmPasswordError,
                        focusRequester = confirmFocus,
                        bringIntoViewRequester = confirmBring,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboard?.hide()
                                focusManager.clearFocus()
                                onAction(ChangePasswordAction.ChangePasswordClicked)
                            }
                        )
                    )


                    Spacer(modifier = Modifier.height(24.dp))

                    AuthButton(
                        text = "Change Password",
                        loading = state.isLoading,
                        enabled = state.isFormValid,
                        onClick = {
                            onAction(ChangePasswordAction.ChangePasswordClicked)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}