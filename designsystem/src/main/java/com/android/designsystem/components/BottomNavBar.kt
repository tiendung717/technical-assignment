package com.android.designsystem.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.designsystem.LocalTradeMeColors

data class BottomNavigationItem(
    @StringRes val text: Int,
    @DrawableRes val icon: Int
)

data class BottomNavBarProps(
    val containerColor: Color,
    val contentColor: Color,
    val selectedContentColor: Color
)

object BottomNavBarDefaults {
    @Composable
    fun properties(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        selectedContentColor: Color = LocalTradeMeColors.current.tasman
    ) = BottomNavBarProps(
        containerColor = containerColor,
        contentColor = contentColor,
        selectedContentColor = selectedContentColor
    )
}

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    props: BottomNavBarProps = BottomNavBarDefaults.properties(),
    items: List<BottomNavigationItem>,
    onItemClicked: (BottomNavigationItem) -> Unit,
    current: BottomNavigationItem? = null,
) {
    Row(
        modifier = modifier
            .background(props.containerColor)
            .navigationBarsPadding()
    ) {
        items.forEach {
            BottomNavItem(
                modifier = Modifier.weight(1f),
                title = stringResource(it.text),
                icon = painterResource(it.icon),
                isSelected = it == current,
                onClick = { onItemClicked(it) },
                contentColor = props.contentColor,
                selectedContentColor = props.selectedContentColor
            )
        }
    }
}

@Composable
internal fun BottomNavItem(
    title: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color,
    selectedContentColor: Color
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = if (isSelected) selectedContentColor else contentColor
        )

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isSelected) selectedContentColor else contentColor
        )
    }
}