package com.example.crudfirebase.appFirebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.data.repository.ProductRepository
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import java.util.UUID

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    var products = mutableStateOf<List<ProductModel>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun loadProducts() {

        isLoading.value = true

        repository.getProducts(
            onSuccess = {
                products.value = it
                isLoading.value = false
            },
            onError = {
                isLoading.value = false
            }
        )
    }

    fun saveProduct(
        name: String,
        description: String,
        price: Double,
        onSuccess: () -> Unit
    ) {

        val id = UUID.randomUUID().toString()

        val product = ProductModel(
            id = id,
            name = name,
            description = description,
            price = price
        )

        repository.saveProduct(
            product = product,
            onSuccess = {
                loadProducts()
                onSuccess()
            },
            onError = {}
        )
    }
}