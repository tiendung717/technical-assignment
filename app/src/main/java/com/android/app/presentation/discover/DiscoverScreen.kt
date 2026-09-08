package com.android.app.presentation.discover

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.app.R
import com.android.app.presentation.discover.components.ProductRow
import com.android.designsystem.LocalTradeMeColors
import com.android.designsystem.components.EmptyView
import com.android.designsystem.components.ErrorView
import com.android.designsystem.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            val message = when (event) {
                DiscoverUiEvent.SearchClicked -> R.string.discover_search_clicked
                DiscoverUiEvent.CartClicked -> R.string.discover_cart_clicked
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.discover_title)) },
                actions = {
                    IconButton(onClick = { viewModel.onSearchClicked() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.discover_action_search)
                        )
                    }
                    IconButton(onClick = { viewModel.onCartClicked() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.discover_action_cart)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor = LocalTradeMeColors.current.tasman
                )
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is DiscoverUiState.Loading -> {
                    LoadingView()
                }

                is DiscoverUiState.Error -> {
                    ErrorView(
                        message = stringResource(R.string.discover_error),
                        onRetry = { viewModel.loadProducts() }
                    )
                }

                is DiscoverUiState.Empty -> {
                    EmptyView(message = stringResource(R.string.discover_empty))
                }

                is DiscoverUiState.Products -> {
                    val items = state.items
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(items) { index, product ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ProductRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    imageUrl = product.imageUrl,
                                    location = product.location,
                                    title = product.title,
                                    price = product.price,
                                    isClassified = product.isClassified
                                )

                                if (index < items.lastIndex) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
