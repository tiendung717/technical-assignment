package com.android.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.app.presentation.discover.DiscoverScreen
import com.android.app.presentation.mytrademe.MyTradeMeScreen
import com.android.app.presentation.watchlist.WatchListScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavScreen.Discover,
    ) {
        composable<NavScreen.Discover> {
            DiscoverScreen()
        }

        composable<NavScreen.WatchList> {
            WatchListScreen()
        }

        composable<NavScreen.MyTradeMe> {
            MyTradeMeScreen()
        }
    }
}
