package com.android.app.presentation.root

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.app.R
import com.android.app.navigation.NavScreen

enum class BottomTab(
    val route: Any,
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int
) {
    DISCOVER(NavScreen.Discover, R.string.bottom_nav_discover, R.drawable.ic_search),
    WATCH_LIST(NavScreen.WatchList, R.string.bottom_nav_watchlist, R.drawable.ic_watchlist),
    MY_TRADE_ME(NavScreen.MyTradeMe, R.string.bottom_nav_my_trademe, R.drawable.ic_my_trademe),
}
