package com.food.delivery.data.repoImpl

import com.food.delivery.common.ResultState
import com.food.delivery.common.USER_COLLECTION
import com.food.delivery.data.models.UserData
import com.food.delivery.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    var firebaseAuth : FirebaseAuth,
    var firebaseFireStore : FirebaseFirestore
) : Repo{
    override fun loginWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email,userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        trySend(ResultState.Success("User Logged In Successfully"))
                    } else {
                        if (it.exception != null){
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }
    }

    override fun registerWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.email,userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        firebaseFireStore.collection(USER_COLLECTION)
                            .document(it.result.user?.uid.toString()).set(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    trySend(ResultState.Success("User Registered Successfully"))
                                }else{
                                    if (it.exception != null){
                                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                                    }
                                }
                            }
                        trySend(ResultState.Success("User Registered Successfully"))
                    } else {
                        if (it.exception != null){
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }
    }

}