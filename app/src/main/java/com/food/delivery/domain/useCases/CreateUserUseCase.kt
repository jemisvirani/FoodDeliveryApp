package com.food.delivery.domain.useCases

import com.food.delivery.data.models.UserData
import com.food.delivery.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.food.delivery.common.ResultState

class CreateUserUseCase @Inject constructor(val repo: Repo) {

    fun createUser(userData: UserData) : Flow<ResultState<String>>{
        return repo.registerWithEmailAndPassword(userData)
    }
}