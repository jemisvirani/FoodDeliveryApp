package com.order.food.auth.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.order.food.R
import com.order.food.auth.ui.components.signup.AuthButton
import com.order.food.auth.ui.components.signup.AuthHeader
import com.order.food.auth.ui.components.signup.PasswordStrengthIndicator
import com.order.food.auth.ui.components.signup.SignUpAction
import com.order.food.auth.ui.components.signup.SignUpEvent
import com.order.food.auth.ui.components.signup.SignUpUiState
import com.order.food.auth.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@Composable
fun SignUpRoute(
    onLoginClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->

            when (event) {

                SignUpEvent.NavigateToHome -> {
                    onSignUpSuccess()
                }

                SignUpEvent.NavigateToLogin -> {
                    onLoginClick()
                }

                is SignUpEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    SignUpScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}


@Composable
fun SignUpScreen(
    state: SignUpUiState,
    snackbarHostState: SnackbarHostState,
    onAction: (SignUpAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()


    val nameFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val addressFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val confirmFocus = remember { FocusRequester() }

    val nameBring = remember { BringIntoViewRequester() }
    val emailBring = remember { BringIntoViewRequester() }
    val addressBring = remember { BringIntoViewRequester() }
    val passwordBring = remember { BringIntoViewRequester() }
    val confirmBring = remember { BringIntoViewRequester() }

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
                    title = "Create Account",
                    subtitle = "Create your account to continue."
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {

                Column(modifier = Modifier.fillMaxSize().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "Account Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.buttonColor)
                    )

                    AuthTextField(
                        value = state.fullName,
                        onValueChange = {
                            onAction(SignUpAction.FullNameChanged(it))
                        },
                        label = "Full Name",
                        leadingIcon = Icons.Outlined.Person,
                        error = state.fullNameError,
                        focusRequester = nameFocus,
                        bringIntoViewRequester = nameBring,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )

                    AuthTextField(
                        value = state.email,
                        onValueChange = {
                            onAction(SignUpAction.EmailChanged(it))
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

                    AuthTextField(
                        value = state.address,
                        onValueChange = {
                            onAction(SignUpAction.AddressChanged(it))
                        },
                        label = "Address",
                        leadingIcon = Icons.Outlined.Home,
                        error = state.addressError,
                        focusRequester = addressFocus,
                        bringIntoViewRequester = addressBring,
                        keyboardOptions = KeyboardOptions(
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
                            onAction(SignUpAction.PasswordChanged(it))
                        },
                        passwordVisible = state.isPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(SignUpAction.TogglePassword)
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
                        enter = fadeIn(
                            animationSpec = tween(300)
                        ) +
                                slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = tween(300)
                                ) +
                                expandVertically(
                                    expandFrom = Alignment.Top,
                                    animationSpec = tween(300)
                                ),

                        exit = fadeOut(
                            animationSpec = tween(200)
                        ) +
                                slideOutVertically(
                                    targetOffsetY = { it / 3 },
                                    animationSpec = tween(200)
                                ) +
                                shrinkVertically(
                                    shrinkTowards = Alignment.Top,
                                    animationSpec = tween(200)
                                )
                    ) {

                        PasswordStrengthIndicator(
                            strength = state.passwordStrength
                        )
                    }


                    PasswordTextField(
                        value = state.confirmPassword,
                        onValueChange = {
                            onAction(SignUpAction.ConfirmPasswordChanged(it))
                        },
                        passwordVisible = state.isConfirmPasswordVisible,
                        onTogglePasswordVisibility = {
                            onAction(SignUpAction.ToggleConfirmPassword)
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
                                onAction(SignUpAction.SignUpClicked)
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))


                    AuthButton(
                        text = "Create Account",
                        loading = state.isLoading,
                        enabled = state.isFormValid,
                        onClick = {
                            onAction(SignUpAction.SignUpClicked)
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Already have an account?",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        TextButton(
                            onClick = {
                                onAction(SignUpAction.LoginClicked)
                            }
                        ) {
                            Text(
                                text = "Login",
                                color = Color(0xFFFF6F00),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

            }



        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .formField(
            focusRequester = focusRequester,
            bringIntoViewRequester = bringIntoViewRequester,
            scope = scope
        ),
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        singleLine = true,
        isError = error != null,
        supportingText = {
            AnimatedVisibility(error != null) {
                Text(error ?: "", color = colorResource(R.color.buttonColor))
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF6F00),
            focusedLabelColor = Color(0xFFFF6F00),
            focusedLeadingIconColor = Color(0xFFFF6F00),
            cursorColor = Color(0xFFFF6F00),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester
) {

    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .formField(
                focusRequester = focusRequester,
                bringIntoViewRequester = bringIntoViewRequester,
                scope = scope
            ),
        label = {
            Text(label)
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        trailingIcon = {

            IconButton(
                onClick = onTogglePasswordVisibility
            ) {

                Icon(
                    imageVector =
                        if (passwordVisible)
                            Icons.Outlined.Visibility
                        else
                            Icons.Outlined.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        isError = error != null,
        supportingText = {
            AnimatedVisibility(error != null) {
                Text(error ?: "",color = colorResource(R.color.buttonColor))
            }
        },
        visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF6F00),
            focusedLabelColor = Color(0xFFFF6F00),
            focusedLeadingIconColor = Color(0xFFFF6F00),
            cursorColor = Color(0xFFFF6F00),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}


fun Modifier.formField(
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope
): Modifier = composed {
    this
        .focusRequester(focusRequester)
        .bringIntoViewRequester(bringIntoViewRequester)
        .onFocusChanged { state ->
            if (state.isFocused) {
                scope.launch {
                    awaitFrame()
                    bringIntoViewRequester.bringIntoView()
                }
            }
        }
}



