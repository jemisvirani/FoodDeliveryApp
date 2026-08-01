package com.food.delivery.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.food.delivery.auth.viewmodel.ChangePasswordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val imeVisible = WindowInsets.isImeVisible


    val state by viewModel.state.collectAsStateWithLifecycle()


    val keyboardController = LocalSoftwareKeyboardController.current

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackBar) {
        state.snackBar?.let { message ->

            val result = snackBarHostState.showSnackbar(
                message = message,
                actionLabel = "OK"
            )

            viewModel.clearSnackBar()

            if (result == SnackbarResult.ActionPerformed ||
                result == SnackbarResult.Dismissed
            ) {
                navController.popBackStack()
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            ChangePasswordHeader(
                imeVisible = imeVisible,
                onBackClick = { navController.popBackStack() })

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (imeVisible) 8.dp else 235.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                shape = RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                val passwordFocus = remember { FocusRequester() }
                val confirmPasswordFocus = remember { FocusRequester() }
                val buttonFocus = remember { FocusRequester() }

                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {

                        item {
                            Text(
                                text = "PASSWORD",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            PasswordField(
                                focusRequester = passwordFocus,
                                imeAction = ImeAction.Next,
                                value = state.newPassword,
                                onValueChange = viewModel::onPasswordChange,
                                placeholder = "Enter new password",
                                visible = passwordVisible,
                                onVisibleChange = {
                                    passwordVisible = !passwordVisible
                                },
                                isError = state.newPasswordError != null,
                                errorMessage = state.newPasswordError,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        scope.launch {
                                            listState.animateScrollToItem(1)
                                            confirmPasswordFocus.requestFocus()
                                        }
                                    }
                                )
                            )
                        }

                        item {
                            Text(
                                text = "CONFIRM PASSWORD",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            PasswordField(
                                focusRequester = confirmPasswordFocus,
                                imeAction = ImeAction.Done,
                                value = state.confirmPassword,
                                onValueChange = viewModel::onConfirmPasswordChange,
                                placeholder = "Confirm password",
                                visible = confirmPasswordVisible,
                                onVisibleChange = {
                                    confirmPasswordVisible = !confirmPasswordVisible
                                },
                                isError = state.confirmPasswordError != null,
                                errorMessage = state.confirmPasswordError,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                )
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(35.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                focusManager.clearFocus(force = true)
                                keyboardController?.hide()

                                viewModel.changePassword {
                                    navController.popBackStack()
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
                                text = "SAVE",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                    }

                }

            }
        }
    }
}

@Composable
fun ChangePasswordHeader(imeVisible: Boolean, onBackClick: () -> Boolean) {
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
                text = "Change Password",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create your new password",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
        }
    }
}


