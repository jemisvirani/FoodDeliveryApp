package com.order.food.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.order.food.auth.domain.repository.AuthRepository
import com.order.food.auth.ui.components.login.LoginUiState
import com.order.food.auth.domain.util.UserPreferences
import com.order.food.auth.ui.components.login.LoginAction
import com.order.food.auth.ui.components.login.LoginEvent
import com.order.food.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onAction(action: LoginAction) {
        when (action) {

            is LoginAction.EmailChanged ->
                updateEmail(action.value)

            is LoginAction.PasswordChanged ->
                updatePassword(action.value)

            LoginAction.TogglePassword ->
                togglePasswordVisibility()

            LoginAction.LoginClicked ->
                login()

            LoginAction.ForgotPasswordClicked ->
                navigateToForgotPassword()

            LoginAction.SignUpClicked ->
                navigateToSignUp()

            is LoginAction.RememberMeChanged ->
                updateRememberMe(action.checked)
        }    }



    private fun updateEmail(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                emailError = AuthValidation.validateEmail(value)
            )
        }
    }

    private fun updatePassword(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = AuthValidation.validatePassword(value)
            )
        }
    }

    private fun updateRememberMe(checked: Boolean) {
        _uiState.update {
            it.copy(rememberMe = checked)
        }
    }

    private fun togglePasswordVisibility() {

        _uiState.update {

            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun navigateToForgotPassword() {

        viewModelScope.launch {
            _event.emit(
                LoginEvent.NavigateToForgotPassword
            )
        }
    }

    private fun navigateToSignUp() {

        viewModelScope.launch {
            _event.emit(
                LoginEvent.NavigateToSignUp
            )
        }
    }

    private fun login() {

        val state = _uiState.value

        val emailError = AuthValidation.validateEmail(state.email)
        val passwordError = AuthValidation.validatePassword(state.password)

        if (emailError != null || passwordError != null) {

            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }

            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            repository.login(
                email = state.email,
                password = state.password
            ).onSuccess {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(LoginEvent.NavigateToHome)

            }.onFailure { throwable ->

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(
                    LoginEvent.ShowSnackbar(
                        throwable.message ?: "Login failed"
                    )
                )
            }
        }
    }
}