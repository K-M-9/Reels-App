package com.app.reelsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.reelsapp.authentication.presentation.navigation.addAuthenticationGraph
import com.app.reelsapp.reels.presentation.screen.ReelsScreen


@Composable
fun NavGraph(startDestination: Routes) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        addAuthenticationGraph(navController)

        composable<Routes.Reels> { ReelsScreen() }
    }
}