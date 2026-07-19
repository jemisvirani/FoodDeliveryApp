package com.food.delivery.auth.screens

import android.util.Log
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.food.delivery.auth.viewmodel.SignUpViewModel
import com.food.delivery.presentation.navigation.Routes
import com.food.delivery.presentation.navigation.SubNavigation

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit
) {

    val HeaderColor = Color(0xFF121223)
    val CardColor = Color.White
    val TextFieldColor = Color(0xFFF4F6FA)
    val ButtonColor = Color(0xFFFF7622)
    val HintColor = Color(0xFF98A0B4)


    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusManager.clearFocus(force = true)
        keyboardController?.hide()
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(HeaderColor)
            ) {

                Spacer(modifier = Modifier.height(45.dp))

                Box(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Sign Up",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please sign up to get started",
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
                shape = RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {

                    Text(
                        text = "FULL NAME",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AppTextField(
                        value = state.fullName,
                        onValueChange = viewModel::onNameChange,
                        placeholder = "Enter your full name",
                        isError = state.fullNameError != null,
                        errorMessage = state.fullNameError
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "EMAIL",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.Black
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
                        text = "ADDRESS",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AppTextField(
                        value = state.address,
                        onValueChange = viewModel::onAddressChange,
                        placeholder = "Enter your address",
                        isError = state.addressError != null,
                        errorMessage = state.addressError
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

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "CONFIRM PASSWORD",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PasswordField(
                        value = state.confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        placeholder = "Confirm your password",
                        visible = confirmPasswordVisible,
                        onVisibleChange = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        },
                        isError = state.confirmPasswordError != null,
                        errorMessage = state.confirmPasswordError
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(onClick = {
                        if (!state.isLoading) {
                            viewModel.signUp {
                                navController.navigate(Routes.DeliveryScreen) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },enabled = !state.isLoading,
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
                                text = "SIGN UP",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Already have an account? ",
                            color = Color.Gray
                        )

                        Text(
                            text = "Login",
                            color = Color(0xFFFF7622),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                onLoginClick()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }
    }



}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    errorMessage: String? = null
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            singleLine = true,

            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF9E9E9E),
                    fontSize = 15.sp
                )
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),

            shape = RoundedCornerShape(16.dp),

            isError = isError,

            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = Color(0xFFF5F5F8),
                unfocusedContainerColor = Color(0xFFF5F5F8),
                errorContainerColor = Color(0xFFF5F5F8),

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red,

                cursorColor = Color.Black,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        if (isError && errorMessage != null) {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visible: Boolean,
    onVisibleChange: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {

    Column {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            singleLine = true,

            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF98A0B4),
                    fontSize = 15.sp
                )
            },

            visualTransformation =
                if (visible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            trailingIcon = {

                IconButton(
                    onClick = onVisibleChange
                ) {

                    Icon(
                        imageVector =
                            if (visible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                        contentDescription = null
                    )

                }
            },

            shape = RoundedCornerShape(16.dp),

            isError = isError,

            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = Color(0xFFF5F5F8),
                unfocusedContainerColor = Color(0xFFF5F5F8),

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                focusedPlaceholderColor = Color(0xFF98A0B4),
                unfocusedPlaceholderColor = Color(0xFF98A0B4),

                cursorColor = Color.Black,

                focusedTrailingIconColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Gray
            )
        )

        if (isError && errorMessage != null) {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

