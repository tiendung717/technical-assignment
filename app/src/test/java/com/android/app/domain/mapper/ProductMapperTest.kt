package com.android.app.domain.mapper

import com.android.app.data.api.model.ListingResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductMapperTest {

    private val mapper = ProductMapper()

    @Test
    fun toProduct_usesRegionAsLocation() {
        val product = mapper.toProduct(listing(region = "Auckland", suburb = "North Shore"))

        assertEquals("Auckland", product.location)
    }

    @Test
    fun toProduct_blankRegion_returnsEmptyLocation() {
        val product = mapper.toProduct(listing(region = null))

        assertEquals("", product.location)
    }

    @Test
    fun toProduct_prefersFirstPhotoUrlForImage() {
        val product = mapper.toProduct(
            listing(
                photoUrls = listOf("https://cdn/first.jpg", "https://cdn/second.jpg"),
                pictureHref = "https://cdn/thumb"
            )
        )

        assertEquals("https://cdn/first.jpg", product.imageUrl)
    }

    @Test
    fun toProduct_noPhotoUrls_fallsBackToPictureHref() {
        val product = mapper.toProduct(
            listing(photoUrls = null, pictureHref = "https://cdn/thumb")
        )

        assertEquals("https://cdn/thumb", product.imageUrl)
    }

    @Test
    fun toProduct_skipsBlankPhotoUrls() {
        val product = mapper.toProduct(
            listing(photoUrls = listOf("", "  "), pictureHref = "https://cdn/thumb")
        )

        assertEquals("https://cdn/thumb", product.imageUrl)
    }

    @Test
    fun toProduct_noImageAnywhere_returnsNull() {
        val product = mapper.toProduct(listing(photoUrls = emptyList(), pictureHref = ""))

        assertNull(product.imageUrl)
    }

    @Test
    fun toProduct_usesPriceDisplayVerbatim() {
        val product = mapper.toProduct(listing(priceDisplay = "Enquiries over \$915,000"))

        assertEquals("Enquiries over \$915,000", product.price)
    }

    @Test
    fun toProduct_noBuyNowPrice_returnsNull() {
        val product = mapper.toProduct(listing(buyNowPrice = null))

        assertNull(product.buyNowPrice)
    }

    @Test
    fun toProduct_wholeBuyNowPrice_formatsWithoutDecimals() {
        val product = mapper.toProduct(listing(buyNowPrice = 2499.0))

        assertEquals("$2,499", product.buyNowPrice)
    }

    @Test
    fun toProduct_fractionalBuyNowPrice_formatsWithTwoDecimals() {
        val product = mapper.toProduct(listing(buyNowPrice = 54.95))

        assertEquals("$54.95", product.buyNowPrice)
    }

    @Test
    fun toProduct_classified_hasNoReserveState() {
        val product = mapper.toProduct(listing(isClassified = true, isReserveMet = null))

        assertNull(product.isReserveMet)
        assertTrue(product.isClassified)
    }

    @Test
    fun toProduct_auctionWithReserveMet_isReserveMet() {
        val product = mapper.toProduct(listing(isClassified = null, isReserveMet = true))

        assertEquals(true, product.isReserveMet)
        assertFalse(product.isClassified)
    }

    @Test
    fun toProduct_auctionWithoutReserveMetFlag_isNotReserveMet() {
        val product = mapper.toProduct(listing(isClassified = null, isReserveMet = null))

        assertEquals(false, product.isReserveMet)
    }

    @Test
    fun toProduct_nullTitle_returnsEmptyTitle() {
        val product = mapper.toProduct(listing(title = null))

        assertEquals("", product.title)
    }

    private fun listing(
        title: String? = "A listing",
        region: String? = "Auckland",
        suburb: String? = "North Shore",
        priceDisplay: String? = "$100",
        pictureHref: String? = null,
        photoUrls: List<String>? = null,
        buyNowPrice: Double? = null,
        isReserveMet: Boolean? = null,
        isClassified: Boolean? = null
    ) = ListingResponse(
        listingId = 1L,
        title = title,
        region = region,
        suburb = suburb,
        priceDisplay = priceDisplay,
        pictureHref = pictureHref,
        photoUrls = photoUrls,
        buyNowPrice = buyNowPrice,
        isReserveMet = isReserveMet,
        isClassified = isClassified
    )
}
