package com.android.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.app.presentation.home.HomeScreen

@Composable
fun MainNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home,
    ) {
        composable<NavScreen.Home> {
            HomeScreen()
        }
    }
}
