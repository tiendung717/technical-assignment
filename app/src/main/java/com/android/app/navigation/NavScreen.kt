package com.android.app.navigation

import kotlinx.serialization.Serializable

object NavScreen {

    @Serializable
    data object Discover

    @Serializable
    data object Notification

    @Serializable
    data object WatchList

    @Serializable
    data object MyTradeMe

}
