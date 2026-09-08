package com.android.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.designsystem.AppTheme
import com.android.designsystem.LocalTradeMeColors

@Composable
fun EmptyView(
    message: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: ImageVector? = Icons.Outlined.ShoppingCart,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = LocalTradeMeColors.current.textDark,
                modifier = Modifier.size(48.dp),
            )
        }
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = LocalTradeMeColors.current.textDark,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalTradeMeColors.current.textLight,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun EmptyViewPreview() {
    AppTheme {
        Surface {
            EmptyView(
                title = "No results",
                message = "No products found"
            )
        }
    }
}

@Preview
@Composable
private fun EmptyViewMessageOnlyPreview() {
    AppTheme {
        Surface {
            EmptyView(
                message = "Nothing to show yet",
                icon = null
            )
        }
    }
}
