package com.android.app.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.common.ResultState
import com.android.app.domain.model.Product
import com.android.app.domain.usecase.GetLastProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getLastProducts: GetLastProductsUseCase
) : ViewModel() {

    val products: StateFlow<ResultState<List<Product>>>
        field = MutableStateFlow<ResultState<List<Product>>>(ResultState.Idle)

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            products.value = ResultState.Loading
            products.value = getLastProducts()
        }
    }
}
