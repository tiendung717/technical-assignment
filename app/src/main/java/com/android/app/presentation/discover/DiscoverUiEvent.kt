package com.android.app.presentation.discover

sealed interface DiscoverUiEvent {

    data object SearchClicked : DiscoverUiEvent

    data object CartClicked : DiscoverUiEvent
}
