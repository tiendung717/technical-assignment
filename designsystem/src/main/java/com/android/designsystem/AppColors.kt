package com.android.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TradeMeColors(
    val tasman: Color,
    val feijoa: Color,
    val textDark: Color,
    val textLight: Color
)

val tradeColors = TradeMeColors(
    tasman = Color(0xFF148FE2),
    feijoa = Color(0xFF29A754),
    textDark = Color(0xFF393531),
    textLight = Color(0xFF85807B)
)
val LocalTradeMeColors = staticCompositionLocalOf { tradeColors }