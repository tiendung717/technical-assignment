package com.android.app.data.api.service

import com.android.app.data.api.model.LatestListingsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("listings/latest.json")
    suspend fun getLatestListings(@Query("rows") rows: Int): LatestListingsResponse
}
