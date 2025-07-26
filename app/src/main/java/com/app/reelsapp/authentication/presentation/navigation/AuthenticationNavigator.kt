package com.app.reelsapp.authentication.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.reelsapp.authentication.presentation.screen.login.LoginScreen
import com.app.reelsapp.authentication.presentation.screen.register.RegisterScreen
import com.app.reelsapp.core.presentation.navigation.Routes


fun NavGraphBuilder.addAuthenticationGraph(navController: NavController) {

    navigation<Routes.Authentication>(
        startDestination = Routes.LoginScreen
    ) {
        composable<Routes.LoginScreen> {
            LoginScreen(
                navigateToHomeScreen = { navController.navigateToHome() },
                navigateToRegisterScreen = { navController.navigate(Routes.RegisterScreen) }
            )
        }

        composable<Routes.RegisterScreen> {
            RegisterScreen(
                navigateToHomeScreen = { navController.navigateToHome() }
            )
        }
    }
}

private fun NavController.navigateToHome() {
    navigate(Routes.Reels) {
        popUpTo(Routes.Authentication) { inclusive = true }
    }
}