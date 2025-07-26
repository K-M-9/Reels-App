package com.app.reelsapp.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    object Authentication : Routes

    @Serializable
    object LoginScreen : Routes

    @Serializable
    object RegisterScreen : Routes

    @Serializable
    object Reels : Routes

}