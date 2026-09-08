package com.android.app.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestListingsResponse(

    @SerialName("TotalCount")
    val totalCount: Int = 0,

    @SerialName("Page")
    val page: Int = 0,

    @SerialName("PageSize")
    val pageSize: Int = 0,

    @SerialName("List")
    val list: List<ListingResponse> = emptyList(),
)
