package com.example.crudfirebase.appFirebase.ui.views.product.model

data class OrderModel(
    val orderId: String = "",
    val userId: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val customerEmail: String = "",
    val products: List<OrderProduct> = emptyList(),
    val total: Double = 0.0,
    val status: String = "Pendiente",
    val date: Long = System.currentTimeMillis()
)
