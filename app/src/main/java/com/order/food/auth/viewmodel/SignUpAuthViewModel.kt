package com.order.food.auth.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.order.food.auth.domain.repository.AuthRepository
import com.order.food.auth.ui.components.changepass.ChangePasswordAction
import com.order.food.auth.ui.components.signup.SignUpAction
import com.order.food.auth.ui.components.signup.SignUpUiState
import com.order.food.auth.ui.components.signup.SignUpEvent
import com.order.food.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event = _event.asSharedFlow()

    fun onAction(action: SignUpAction) {

        when (action) {

            is SignUpAction.FullNameChanged ->
                updateFullName(action.value)

            is SignUpAction.EmailChanged ->
                updateEmail(action.value)

            is SignUpAction.AddressChanged ->
                updateAddress(action.value)

            is SignUpAction.PasswordChanged ->
                updatePassword(action.value)

            is SignUpAction.ConfirmPasswordChanged ->
                updateConfirmPassword(action.value)

            SignUpAction.TogglePassword ->
                togglePasswordVisibility()

            SignUpAction.ToggleConfirmPassword ->
                toggleConfirmPasswordVisibility()

            SignUpAction.SignUpClicked ->
                signUp()

            SignUpAction.LoginClicked ->
                navigateToLogin()
        }
    }

    private fun updateFullName(value: String) {
        _uiState.update {
            it.copy(
                fullName = value,
                fullNameError = AuthValidation.validateName(value)
            )
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

    private fun updateAddress(value: String) {
        _uiState.update {
            it.copy(
                address = value,
                addressError = AuthValidation.validateAddress(value)
            )
        }
    }

    private fun updatePassword(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = AuthValidation.validatePassword(value),
                passwordStrength = AuthValidation.passwordStrength(value),
                confirmPasswordError = AuthValidation.validateConfirmPassword(
                    value,
                    it.confirmPassword
                )
            )
        }
    }

    private fun updateConfirmPassword(value: String) {
        _uiState.update {
            it.copy(
                confirmPassword = value,
                confirmPasswordError = AuthValidation.validateConfirmPassword(
                    it.password,
                    value
                )
            )
        }
    }

    private fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        _uiState.update {
            it.copy(
                isConfirmPasswordVisible = !it.isConfirmPasswordVisible
            )
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _event.emit(SignUpEvent.NavigateToLogin)
        }
    }

    private fun signUp() {

        val state = _uiState.value

        val nameError = AuthValidation.validateName(state.fullName)
        val emailError = AuthValidation.validateEmail(state.email)
        val addressError = AuthValidation.validateAddress(state.address)
        val passwordError = AuthValidation.validatePassword(state.password)
        val confirmError = AuthValidation.validateConfirmPassword(
            state.password,
            state.confirmPassword
        )

        if (
            nameError != null ||
            emailError != null ||
            addressError != null ||
            passwordError != null ||
            confirmError != null
        ) {

            _uiState.update {
                it.copy(
                    fullNameError = nameError,
                    emailError = emailError,
                    addressError = addressError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmError
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

            repository.signUp(
                fullName = state.fullName,
                email = state.email,
                address = state.address,
                password = state.password
            ).onSuccess {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(SignUpEvent.NavigateToHome)

            }.onFailure { throwable ->

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(
                    SignUpEvent.ShowSnackbar(
                        throwable.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
}