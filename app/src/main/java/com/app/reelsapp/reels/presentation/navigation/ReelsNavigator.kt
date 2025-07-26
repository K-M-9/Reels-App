package com.app.reelsapp.reels.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.reelsapp.core.presentation.navigation.Routes
import com.app.reelsapp.reels.presentation.screen.ReelsScreen

fun NavGraphBuilder.addReelsGraph(navController: NavController){

    navigation<Routes.Reels>(
        startDestination = Routes.Reels
    ){
        composable<Routes.Reels> {
            ReelsScreen()
        }
    }
}