package com.android.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TradeMeColors(
    val tasman: Color,
    val feijoa: Color,
    val textDark: Color,
    val textLight: Color,
    val background: Color,
    val placeholder: Color
)

val tradeMeColors = TradeMeColors(
    tasman = Color(0xFF148FE2),
    feijoa = Color(0xFF29A754),
    textDark = Color(0xFF393531),
    textLight = Color(0xFF85807B),
    background = Color(0xFFFFFFFF),
    placeholder = Color(0xFFEDEBE9)
)
val LocalTradeMeColors = staticCompositionLocalOf { tradeMeColors }
