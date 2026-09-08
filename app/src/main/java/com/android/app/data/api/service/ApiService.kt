package com.android.app.data.api.service

import com.android.app.data.api.model.LatestListingsResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val PHOTO_SIZE_LARGE = "Large"

interface ApiService {

    @GET("listings/latest.json")
    suspend fun getLatestListings(
        @Query("rows") rows: Int,
        @Query("photo_size") photoSize: String = PHOTO_SIZE_LARGE
    ): LatestListingsResponse
}
