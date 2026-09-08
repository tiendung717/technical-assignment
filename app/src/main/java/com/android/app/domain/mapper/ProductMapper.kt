package com.android.app.domain.mapper

import com.android.app.data.api.model.ListingResponse
import com.android.app.domain.model.Product
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductMapper @Inject constructor() {

    private val priceFormat: NumberFormat = NumberFormat.getCurrencyInstance(
        Locale.Builder().setLanguage("en").setRegion("NZ").build()
    )

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


    private fun formatPrice(amount: Double): String {
        val digits = if (amount % 1.0 == 0.0) 0 else 2
        return priceFormat.apply {
            minimumFractionDigits = digits
            maximumFractionDigits = digits
        }.format(amount)
    }
}
