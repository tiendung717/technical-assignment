package com.android.app.presentation.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.app.navigation.MainNavigation
import com.android.designsystem.components.BottomNavBar

@Composable
fun RootScreen(
    viewModel: RootViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    LaunchedEffect(backStackEntry) {
        val destination = backStackEntry?.destination ?: return@LaunchedEffect
        val displayed = BottomTab.entries.firstOrNull { tab ->
            destination.hierarchy.any { parent -> parent.hasRoute(tab.route::class) }
        }
        if (displayed != null) {
            viewModel.onTabDisplayed(displayed)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MainNavigation(
            modifier = Modifier.weight(1f),
            navController = navController
        )

        BottomNavBar(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            items = viewModel.navItems,
            current = viewModel.navItemFor(selectedTab),
            onItemClicked = { item ->
                viewModel.onNavItemClicked(item)?.let { tab ->
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}
