package com.food.delivery.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation {
    @Serializable
    object LoginSignUpScreen : SubNavigation()

    @Serializable
    object MainHomeScreen : SubNavigation()

    @Serializable
    object SplashGraph : SubNavigation()
}

sealed class Routes {

    @Serializable
    object SplashScreen : Routes()

    @Serializable
    object LoginScreen : Routes()

    @Serializable
    object SignUpScreen : Routes()

    @Serializable
    object ForgetPasswordScreen : Routes()

    @Serializable
    object ChangePasswordScreen : Routes()

    @Serializable
    object DeliveryScreen : Routes()

    @Serializable
    object QuickScreen : Routes()

    @Serializable
    object DiningScreen : Routes()

    @Serializable
    object ProfileScreen : Routes()

    @Serializable
    data class ParticularCardScreen(
        val source: String
    ) : Routes()

    @Serializable
    object FinalCheckoutScreen : Routes()

    @Serializable
    object SearchBarScreen : Routes()
}



