package com.android.app.common

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun formatPrice_wholeAmount_hasNoDecimals() {
        assertEquals("$2,499", formatPrice(2499.0))
    }

    @Test
    fun formatPrice_fractionalAmount_hasTwoDecimals() {
        assertEquals("$54.95", formatPrice(54.95))
    }

    @Test
    fun formatPrice_singleDecimalPlace_isPaddedToTwo() {
        assertEquals("$9.50", formatPrice(9.5))
    }

    @Test
    fun formatPrice_zero_hasNoDecimals() {
        assertEquals("$0", formatPrice(0.0))
    }

    @Test
    fun formatPrice_groupsThousands() {
        assertEquals("$1,234,567", formatPrice(1234567.0))
    }

    @Test
    fun formatPrice_smallAmountUnderOne_keepsDecimals() {
        assertEquals("$0.99", formatPrice(0.99))
    }

    @Test
    fun formatPrice_negativeAmount_keepsSign() {
        assertEquals("-$25", formatPrice(-25.0))
    }

    @Test
    fun formatPrice_roundsToTwoDecimals() {
        assertEquals("$1.24", formatPrice(1.235))
    }

    @Test
    fun formatPrice_isIndependentOfPreviousCalls() {
        formatPrice(54.95)

        assertEquals("$100", formatPrice(100.0))
    }
}
