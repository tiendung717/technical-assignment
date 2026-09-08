package com.android.app.presentation.discover

import com.android.app.domain.model.Product

sealed interface DiscoverUiState {

    data object Loading : DiscoverUiState

    data object Empty : DiscoverUiState

    data class Error(val throwable: Throwable) : DiscoverUiState

    data class Products(val items: List<Product>) : DiscoverUiState
}
