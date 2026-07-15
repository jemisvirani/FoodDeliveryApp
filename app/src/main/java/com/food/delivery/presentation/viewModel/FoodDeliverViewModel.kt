package com.food.delivery.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.common.ResultState
import com.food.delivery.data.models.UserData
import com.food.delivery.domain.useCases.CreateUserUseCase
import com.food.delivery.domain.useCases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDeliverViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
): ViewModel(){

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asSharedFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asSharedFlow()

    fun createUser(userData: UserData){
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect {
                when(it){
                    is ResultState.Error -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    ResultState.Loading -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserData){
        viewModelScope.launch {
            loginUserUseCase.loginUser(userData).collect {
                when(it){
                    is ResultState.Error -> {
                      _loginScreenState.value = _loginScreenState.value.copy(
                          isLoading = false,
                          errorMessage = it.message
                      )
                    }

                    ResultState.Loading ->{
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success ->{
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }
}

data class SignUpScreenState(
    val isLoading : Boolean =false,
    val errorMessage : String? = null,
    val userData: String? = null
)

data class LoginScreenState(
    val isLoading : Boolean = false,
    val errorMessage: String? = null,
    val userData : String? = null
)