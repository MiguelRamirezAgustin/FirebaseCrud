package com.example.crudfirebase.appFirebase.ui.views.product.model

data class OrderProduct(
    val productId: String = "",
    val productName: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0
)