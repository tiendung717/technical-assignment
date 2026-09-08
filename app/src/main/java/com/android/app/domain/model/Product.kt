package com.android.app.domain.model

data class Product(
    val imageUrl: String?,
    val location: String,
    val title: String,
    val price: String,
    val isAuction: Boolean,
    val isClassified: Boolean
)