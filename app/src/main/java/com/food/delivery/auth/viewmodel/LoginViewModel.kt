package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.food.delivery.auth.ui.state.LoginUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update {
            it.copy(
                email = value,
                emailError = null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _state.update {
            it.copy(
                password = value,
                passwordError = null
            )
        }
    }

    fun onRememberMeChange(checked: Boolean) {
        _state.update {
            it.copy(
                rememberMe = checked
            )
        }
    }

    fun login(
        onSuccess: () -> Unit
    ) {

        val ui = state.value

        val emailError = AuthValidation.email(ui.email)
        val passwordError = AuthValidation.password(ui.password)

        _state.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        if (emailError == null && passwordError == null) {
            onSuccess()
        }
    }
}