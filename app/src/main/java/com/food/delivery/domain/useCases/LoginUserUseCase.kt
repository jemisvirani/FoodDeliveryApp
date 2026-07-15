package com.food.delivery.domain.useCases

import com.food.delivery.common.ResultState
import com.food.delivery.data.models.UserData
import com.food.delivery.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(val repo: Repo) {

    fun loginUser(userData: UserData) : Flow<ResultState<String>>{
        return repo.loginWithEmailAndPassword(userData)
    }
}