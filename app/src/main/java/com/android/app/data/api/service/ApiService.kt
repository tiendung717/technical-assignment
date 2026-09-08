package com.android.app.data.api.service

import com.android.app.data.api.model.LatestListingsResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val PHOTO_SIZE_LARGE = "Large"
private const val LISTED_AS_ALL = "All"
private const val LISTED_AS_AUCTIONS = "Auctions"
private const val LISTED_AS_CLASSIFIED = "Classifieds"

interface ApiService {

    @GET("listings/latest.json")
    suspend fun getLatestListings(
        @Query("rows") rows: Int,
        @Query("photo_size") photoSize: String = PHOTO_SIZE_LARGE,
        @Query("listed_as") listedAs: String = LISTED_AS_ALL
    ): LatestListingsResponse
}
