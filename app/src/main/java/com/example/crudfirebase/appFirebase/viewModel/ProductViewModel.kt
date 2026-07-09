package com.example.crudfirebase.appFirebase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.data.repository.ProductRepository
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        getProducts()
    }

    fun addProduct(product: ProductModel) {
        _isLoading.value = true

        repository.addProduct(
            product,
            onSuccess = {
                _isLoading.value = false
                _message.value = "Producto agregado correctamente"
            },
            onFailure = {
                _isLoading.value = false
                _message.value = it.message ?: "Error"
            }
        )
    }

    fun getProducts() {
        repository.getProductsListener(
            onData = { list ->
                _products.value = list
            },
            onError = {
                _message.value = it.message ?: "Error al cargar productos"
            }
        )
    }

    fun deleteProduct(productId: String) {
        repository.deleteProduct(
            productId,
            onSuccess = {
                _message.value = "Producto eliminado"
            },
            onFailure = {
                _message.value = it.message ?: "Error al eliminar"
            }
        )
    }
}