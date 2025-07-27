package com.app.reelsapp.authentication.presentation.screen.login

sealed interface LoginEffect {
    object NavigateToHomeScreen : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}