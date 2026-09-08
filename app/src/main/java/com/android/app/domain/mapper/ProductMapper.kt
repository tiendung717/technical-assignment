package com.android.app.domain.mapper

import com.android.app.data.api.model.ListingResponse
import com.android.app.domain.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductMapper @Inject constructor() {

    fun toProduct(listing: ListingResponse): Product {
        val classified = listing.isClassified == true

        return Product(
            imageUrl = listing.photoUrls?.firstOrNull { url -> url.isNotBlank() },
            location = listOfNotNull(
                listing.suburb?.takeIf { value -> value.isNotBlank() },
                listing.region?.takeIf { value -> value.isNotBlank() }
            ).joinToString(", "),
            title = listing.title.orEmpty(),
            price = listing.priceDisplay.orEmpty(),
            isClassified = classified
        )
    }
}
