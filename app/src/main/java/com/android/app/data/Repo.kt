package com.android.app.data

import com.android.app.common.safeCall
import com.android.app.data.api.service.ApiService
import com.android.app.domain.mapper.ProductMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val apiService: ApiService,
    private val productMapper: ProductMapper
) {

    suspend fun getLatestProducts(rows: Int) = safeCall {
        apiService.getLatestListings(rows).list.map { listing -> productMapper.toProduct(listing) }
    }
}
