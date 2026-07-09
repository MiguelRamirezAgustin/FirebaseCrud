
package com.example.crudfirebase.appFirebase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.data.repository.OrderRepository
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductOrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderModel>>(emptyList())
    val orders = _orders.asStateFlow()
    fun createOrder(
        order: OrderModel,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        repository.createOrder(
            order = order,
            onSuccess = onSuccess,
            onError = onError
        )
    }

    fun getOrders(){
        repository.getOrders { list ->
            _orders.value = list
        }
    }

}