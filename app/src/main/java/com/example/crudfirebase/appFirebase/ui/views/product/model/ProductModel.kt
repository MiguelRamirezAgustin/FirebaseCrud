package com.example.crudfirebase.appFirebase.ui.views.product.model

data class ProductModel (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val available: Boolean = true
)