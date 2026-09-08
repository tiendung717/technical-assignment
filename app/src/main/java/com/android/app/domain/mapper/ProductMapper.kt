package com.android.app.domain.mapper

import com.android.app.common.formatPrice
import com.android.app.data.api.model.ListingResponse
import com.android.app.domain.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductMapper @Inject constructor() {

    fun toProduct(listing: ListingResponse): Product {
        val classified = listing.isClassified == true

        return Product(
            imageUrl = listing.photoUrls?.firstOrNull { url -> url.isNotBlank() }
                ?: listing.pictureHref?.takeIf { href -> href.isNotBlank() },
            location = listing.region.orEmpty(),
            title = listing.title.orEmpty(),
            price = listing.priceDisplay.orEmpty(),
            buyNowPrice = listing.buyNowPrice?.let { amount -> formatPrice(amount) },
            isReserveMet = if (classified) null else listing.isReserveMet == true,
            isClassified = classified
        )
    }
}
