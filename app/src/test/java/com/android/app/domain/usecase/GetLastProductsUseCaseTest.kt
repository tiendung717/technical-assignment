package com.android.app.domain.usecase

import com.android.app.common.ResultState
import com.android.app.data.Repo
import com.android.app.domain.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class GetLastProductsUseCaseTest {

    private val repo = mockk<Repo>()
    private val useCase = GetLastProductsUseCase(repo)

    @Test
    fun invoke_defaultsToTwentyRows() = runTest {
        coEvery { repo.getLatestProducts(any()) } returns ResultState.Success(emptyList())

        useCase()

        coVerify(exactly = 1) { repo.getLatestProducts(20) }
    }

    @Test
    fun invoke_passesRequestedRowCount() = runTest {
        coEvery { repo.getLatestProducts(any()) } returns ResultState.Success(emptyList())

        useCase(rows = 5)

        coVerify(exactly = 1) { repo.getLatestProducts(5) }
    }

    @Test
    fun invoke_returnsProductsFromRepo() = runTest {
        val products = listOf(product("First"), product("Second"))
        coEvery { repo.getLatestProducts(any()) } returns ResultState.Success(products)

        val result = useCase()

        assertTrue(result is ResultState.Success)
        assertEquals(products, (result as ResultState.Success).data)
    }

    @Test
    fun invoke_emptyList_returnsSuccessWithNoProducts() = runTest {
        coEvery { repo.getLatestProducts(any()) } returns ResultState.Success(emptyList())

        val result = useCase()

        assertTrue(result is ResultState.Success)
        assertTrue((result as ResultState.Success).data.isEmpty())
    }

    @Test
    fun invoke_repoFails_propagatesFailure() = runTest {
        val failure = IOException("no network")
        coEvery { repo.getLatestProducts(any()) } returns ResultState.Failure(failure)

        val result = useCase()

        assertTrue(result is ResultState.Failure)
        assertEquals(failure, (result as ResultState.Failure).throwable)
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
