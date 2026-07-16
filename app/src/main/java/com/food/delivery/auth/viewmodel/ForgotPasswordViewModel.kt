package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.food.delivery.auth.ui.state.ForgotPasswordUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordUiState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update {
            it.copy(
                email = value,
                emailError = null
            )
        }
    }

    fun validateEmail(
        onSuccess: () -> Unit
    ) {

        val emailError = AuthValidation.email(state.value.email)

        _state.update {
            it.copy(
                emailError = emailError
            )
        }

        if (emailError == null) {
            onSuccess()
        }
    }
}