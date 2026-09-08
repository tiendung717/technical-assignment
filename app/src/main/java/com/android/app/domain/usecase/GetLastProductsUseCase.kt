package com.android.app.domain.usecase

import com.android.app.data.Repo
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_ROWS = 20

@Singleton
class GetLastProductsUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend operator fun invoke(rows: Int = DEFAULT_ROWS) = repo.getLatestProducts(rows)
}
