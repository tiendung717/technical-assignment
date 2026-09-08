package com.android.app.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.common.ResultState
import com.android.app.domain.model.Product
import com.android.app.domain.usecase.GetLastProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getLastProducts: GetLastProductsUseCase
) : ViewModel() {

    val uiState: StateFlow<DiscoverUiState>
        field = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)

    val uiEvent: SharedFlow<DiscoverUiEvent>
        field = MutableSharedFlow<DiscoverUiEvent>()

    init {
        loadProducts()
    }

    fun onSearchClicked() {
        viewModelScope.launch {
            uiEvent.emit(DiscoverUiEvent.SearchClicked)
        }
    }

    fun onCartClicked() {
        viewModelScope.launch {
            uiEvent.emit(DiscoverUiEvent.CartClicked)
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            uiState.value = DiscoverUiState.Loading
            uiState.value = getLastProducts().toUiState()
        }
    }

    private fun ResultState<List<Product>>.toUiState(): DiscoverUiState {
        return when (this) {
            is ResultState.Success -> when {
                data.isEmpty() -> DiscoverUiState.Empty
                else -> DiscoverUiState.Products(data)
            }

            is ResultState.Failure -> DiscoverUiState.Error(throwable)
            ResultState.Idle, ResultState.Loading -> DiscoverUiState.Loading
        }
    }
}
