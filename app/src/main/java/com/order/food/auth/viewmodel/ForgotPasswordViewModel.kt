package com.order.food.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.order.food.auth.domain.repository.AuthRepository
import com.order.food.auth.ui.components.forget.ForgotPasswordAction
import com.order.food.auth.ui.components.forget.ForgotPasswordEvent
import com.order.food.auth.ui.components.forget.ForgotPasswordUiState
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
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> =
        _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ForgotPasswordEvent>()
    val event = _event.asSharedFlow()

    fun onAction(action: ForgotPasswordAction) {

        when (action) {

            is ForgotPasswordAction.EmailChanged ->
                updateEmail(action.value)

            ForgotPasswordAction.SendResetLinkClicked ->
                sendResetLink()
        }
    }

    private fun updateEmail(value: String) {

        _uiState.update {

            it.copy(
                email = value,
                emailError = AuthValidation.validateEmail(value)
            )
        }
    }

    private fun sendResetLink() {

        val state = _uiState.value

        val emailError =
            AuthValidation.validateEmail(state.email)

        if (emailError != null) {

            _uiState.update {

                it.copy(
                    emailError = emailError
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

            repository.forgotPassword(
                state.email
            ).onSuccess {

                _uiState.update {

                    it.copy(
                        isLoading = false
                    )
                }

                _event.emit(
                    ForgotPasswordEvent.ShowSnackbar(
                        "Reset link sent successfully."
                    )
                )

                _event.emit(
                    ForgotPasswordEvent.NavigateToLogin
                )

            }.onFailure { throwable ->

                _uiState.update {

                    it.copy(
                        isLoading = false
                    )
                }

                _event.emit(
                    ForgotPasswordEvent.ShowSnackbar(
                        throwable.message ?: "Something went wrong."
                    )
                )
            }
        }
    }
}