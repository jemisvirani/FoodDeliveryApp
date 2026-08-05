package com.order.food.domain.repo

import kotlinx.coroutines.flow.Flow
import com.order.food.common.ResultState
import com.order.food.data.models.UserData

interface Repo {

    fun loginWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>

    fun registerWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>


}