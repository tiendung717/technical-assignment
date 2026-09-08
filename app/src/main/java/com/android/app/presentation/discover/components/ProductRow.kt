package com.android.app.presentation.discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.app.R
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.android.designsystem.AppTheme
import com.android.designsystem.LocalTradeMeColors

@Composable
fun ProductRow(
    modifier: Modifier = Modifier,
    heightDp: Dp = 120.dp,
    imageUrl: String?,
    location: String,
    title: String,
    price: String,
    isClassified: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(heightDp),
            model = imageUrl,
            contentDescription = stringResource(R.string.product_image),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightDp)
        ) {
            Text(
                text = location,
                style = MaterialTheme.typography.bodySmall,
                color = LocalTradeMeColors.current.textLight
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalTradeMeColors.current.textDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isClassified) {
                    Price(
                        amount = price,
                        text = stringResource(R.string.product_no_reserve)
                    )
                }

                Spacer(Modifier.weight(1f))

                if (!isClassified) {
                    Price(
                        amount = price,
                        text = stringResource(R.string.product_buy_now),
                        horizontalAlignment = Alignment.End
                    )
                }
            }
        }
    }
}

@Composable
internal fun Price(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    amount: String,
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = amount,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = LocalTradeMeColors.current.textDark
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalTradeMeColors.current.textLight
        )
    }
}
@OptIn(ExperimentalCoilApi::class)
private val previewImageHandler = AsyncImagePreviewHandler { _ ->
    ColorImage(color = 0xFFB0BEC5.toInt())
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PreviewContainer(content: @Composable () -> Unit) {
    AppTheme {
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewImageHandler) {
            Surface {
                content()
            }
        }
    }
}

@Preview(name = "Auction", showBackground = true, widthDp = 360)
@Composable
private fun ProductRowAuctionPreview() {
    PreviewContainer {
        ProductRow(
            modifier = Modifier.padding(16.dp),
            imageUrl = "https://example.com/product.jpg",
            location = "Auckland City, Auckland",
            title = "Apple iPhone 15 Pro 256GB Natural Titanium",
            price = "$1,299",
            isClassified = false
        )
    }
}

@Preview(name = "Classified", showBackground = true, widthDp = 360)
@Composable
private fun ProductRowClassifiedPreview() {
    PreviewContainer {
        ProductRow(
            modifier = Modifier.padding(16.dp),
            imageUrl = "https://example.com/product.jpg",
            location = "Christchurch, Canterbury",
            title = "Vintage Rimu Dining Table",
            price = "$450",
            isClassified = true
        )
    }
}

@Preview(name = "Auction and classified", showBackground = true, widthDp = 360)
@Composable
private fun ProductRowBothPreview() {
    PreviewContainer {
        ProductRow(
            modifier = Modifier.padding(16.dp),
            imageUrl = "https://example.com/product.jpg",
            location = "Wellington Central, Wellington",
            title = "Trek Marlin 7 Mountain Bike",
            price = "$899",
            isClassified = true
        )
    }
}

@Preview(name = "Long title, no image", showBackground = true, widthDp = 360)
@Composable
private fun ProductRowLongTitlePreview() {
    PreviewContainer {
        ProductRow(
            modifier = Modifier.padding(16.dp),
            imageUrl = null,
            location = "Hamilton, Waikato",
            title = "Ercol Windsor Quaker Armchair in original fabric, fully restored, " +
                "collection only from Hamilton",
            price = "$1,150",
            isClassified = false
        )
    }
}

@Preview(name = "Price", showBackground = true)
@Composable
private fun PricePreview() {
    PreviewContainer {
        Price(
            modifier = Modifier.padding(16.dp),
            amount = "$1,299",
            text = "Buy Now"
        )
    }
}
