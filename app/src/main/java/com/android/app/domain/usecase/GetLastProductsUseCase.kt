package com.android.app.domain.usecase

import com.android.app.data.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLastProductsUseCase @Inject constructor(
    private val repo: Repo
) {
}