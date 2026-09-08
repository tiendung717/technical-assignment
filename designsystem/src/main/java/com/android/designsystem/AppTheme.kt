package com.android.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTradeMeColors provides tradeColors
    ) {
        MaterialTheme(
            content = content
        )
    }
}
