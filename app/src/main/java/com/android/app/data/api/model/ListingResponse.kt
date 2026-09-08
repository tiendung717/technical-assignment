package com.android.app.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingResponse(

    @SerialName("ListingId")
    val listingId: Long = 0L,

    @SerialName("Title")
    val title: String? = null,

    @SerialName("Region")
    val region: String? = null,

    @SerialName("Suburb")
    val suburb: String? = null,

    @SerialName("PriceDisplay")
    val priceDisplay: String? = null,

    @SerialName("PictureHref")
    val pictureHref: String? = null,

    @SerialName("PhotoUrls")
    val photoUrls: List<String>? = null,

    @SerialName("BuyNowPrice")
    val buyNowPrice: Double? = null,

    @SerialName("ReserveState")
    val reserveState: Int? = null,

    @SerialName("IsClassified")
    val isClassified: Boolean? = null,
)
