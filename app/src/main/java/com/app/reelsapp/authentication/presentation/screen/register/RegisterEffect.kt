package com.app.reelsapp.authentication.presentation.screen.register

sealed interface RegisterEffect {
    object NavigateToHomeScreen : RegisterEffect
}