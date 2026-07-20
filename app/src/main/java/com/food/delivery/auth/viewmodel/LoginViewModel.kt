package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.ui.state.LoginUiState
import com.food.delivery.auth.domain.util.UserPreferences
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    init {
        viewModelScope.launch {

            val remember = userPreferences.isRememberMe()
            val email = userPreferences.getEmail()

            _state.update {
                it.copy(
                    rememberMe = remember,
                    email = email
                )
            }
        }
    }

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

    fun clearSnackBar() {
        _state.update {
            it.copy(
                snackBar = null
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

        if (emailError != null || passwordError != null) {
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            repository.login(
                email = ui.email,
                password = ui.password
            ).onSuccess {

                if (ui.rememberMe) {
                    userPreferences.saveRememberMe(
                        rememberMe = true,
                        email = ui.email
                    )
                } else {
                    userPreferences.clearRememberMe()
                }

                _state.update {
                    it.copy(isLoading = false)
                }

                onSuccess()

            }.onFailure { exception ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        snackBar = exception.message ?: "Login failed"
                    )
                }
            }
        }
    }
}