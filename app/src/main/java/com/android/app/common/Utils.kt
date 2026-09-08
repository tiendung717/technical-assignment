package com.android.app.common

import java.text.NumberFormat
import java.util.Locale

private val NZ_LOCALE: Locale = Locale.Builder().setLanguage("en").setRegion("NZ").build()

fun formatPrice(amount: Double): String {
    val digits = if (amount % 1.0 == 0.0) 0 else 2

    return NumberFormat.getCurrencyInstance(NZ_LOCALE).apply {
        minimumFractionDigits = digits
        maximumFractionDigits = digits
    }.format(amount)
}
