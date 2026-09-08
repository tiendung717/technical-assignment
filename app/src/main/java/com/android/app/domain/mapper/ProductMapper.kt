package com.android.app.domain.mapper

import com.android.app.data.api.model.ListingResponse
import com.android.app.domain.model.Product
import com.android.app.domain.model.ReserveState
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

private const val RESERVE_STATE_NONE = 0
private const val RESERVE_STATE_MET = 1
private const val RESERVE_STATE_NOT_MET = 2

@Singleton
class ProductMapper @Inject constructor() {

    private val priceFormat: NumberFormat = NumberFormat.getCurrencyInstance(
        Locale.Builder().setLanguage("en").setRegion("NZ").build()
    )

    fun toProduct(listing: ListingResponse): Product {
        return Product(
            imageUrl = listing.photoUrls?.firstOrNull { url -> url.isNotBlank() }
                ?: listing.pictureHref?.takeIf { href -> href.isNotBlank() },
            location = listing.region.orEmpty(),
            title = listing.title.orEmpty(),
            price = listing.priceDisplay.orEmpty(),
            buyNowPrice = listing.buyNowPrice?.let { amount -> formatPrice(amount) },
            reserveState = toReserveState(listing.reserveState),
            isClassified = listing.isClassified == true
        )
    }

    private fun toReserveState(value: Int?): ReserveState {
        return when (value) {
            RESERVE_STATE_NONE -> ReserveState.NONE
            RESERVE_STATE_MET -> ReserveState.MET
            RESERVE_STATE_NOT_MET -> ReserveState.NOT_MET
            else -> ReserveState.NOT_APPLICABLE
        }
    }

    private fun formatPrice(amount: Double): String {
        val digits = if (amount % 1.0 == 0.0) 0 else 2
        return priceFormat.apply {
            minimumFractionDigits = digits
            maximumFractionDigits = digits
        }.format(amount)
    }
}
