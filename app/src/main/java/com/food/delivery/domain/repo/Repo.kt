package com.food.delivery.domain.repo

import kotlinx.coroutines.flow.Flow
import com.food.delivery.common.ResultState
import com.food.delivery.data.models.UserData

interface Repo {

    fun loginWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>

    fun registerWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>


}