package com.android.app.presentation.discover

import com.android.app.common.ResultState
import com.android.app.domain.model.Product
import com.android.app.domain.usecase.GetLastProductsUseCase
import com.android.app.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DiscoverViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getLastProducts = mockk<GetLastProductsUseCase>()

    @Test
    fun init_loadsProductsOnce() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(listOf(product("First")))

        DiscoverViewModel(getLastProducts)

        coVerify(exactly = 1) { getLastProducts() }
    }

    @Test
    fun init_withProducts_emitsProductsState() = runTest {
        val products = listOf(product("First"), product("Second"))
        coEvery { getLastProducts() } returns ResultState.Success(products)

        val viewModel = DiscoverViewModel(getLastProducts)

        val state = viewModel.uiState.value
        assertTrue(state is DiscoverUiState.Products)
        assertEquals(products, (state as DiscoverUiState.Products).items)
    }

    @Test
    fun init_withEmptyList_emitsEmptyState() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(emptyList())

        val viewModel = DiscoverViewModel(getLastProducts)

        assertEquals(DiscoverUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun init_whenUseCaseFails_emitsErrorStateCarryingThrowable() = runTest {
        val failure = IOException("no network")
        coEvery { getLastProducts() } returns ResultState.Failure(failure)

        val viewModel = DiscoverViewModel(getLastProducts)

        val state = viewModel.uiState.value
        assertTrue(state is DiscoverUiState.Error)
        assertEquals(failure, (state as DiscoverUiState.Error).throwable)
    }

    @Test
    fun loadProducts_setsLoadingWhileFetching() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(emptyList())
        val viewModel = DiscoverViewModel(getLastProducts)

        var stateWhileFetching: DiscoverUiState? = null
        coEvery { getLastProducts() } coAnswers {
            stateWhileFetching = viewModel.uiState.value
            ResultState.Success(listOf(product("First")))
        }

        viewModel.loadProducts()

        assertEquals(DiscoverUiState.Loading, stateWhileFetching)
        assertTrue(viewModel.uiState.value is DiscoverUiState.Products)
    }

    @Test
    fun loadProducts_calledAgain_refetches() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(emptyList())
        val viewModel = DiscoverViewModel(getLastProducts)

        viewModel.loadProducts()

        coVerify(exactly = 2) { getLastProducts() }
    }

    @Test
    fun onSearchClicked_emitsSearchEvent() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(emptyList())
        val viewModel = DiscoverViewModel(getLastProducts)

        val events = mutableListOf<DiscoverUiEvent>()
        val collector = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEvent.toList(events)
        }

        viewModel.onSearchClicked()
        collector.cancel()

        assertEquals(listOf(DiscoverUiEvent.SearchClicked), events)
    }

    @Test
    fun onCartClicked_emitsCartEvent() = runTest {
        coEvery { getLastProducts() } returns ResultState.Success(emptyList())
        val viewModel = DiscoverViewModel(getLastProducts)

        val events = mutableListOf<DiscoverUiEvent>()
        val collector = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiEvent.toList(events)
        }

        viewModel.onCartClicked()
        collector.cancel()

        assertEquals(listOf(DiscoverUiEvent.CartClicked), events)
    }

    private fun product(title: String) = Product(
        imageUrl = null,
        location = "Auckland",
        title = title,
        price = "$10",
        buyNowPrice = null,
        isReserveMet = null,
        isClassified = false
    )
}
