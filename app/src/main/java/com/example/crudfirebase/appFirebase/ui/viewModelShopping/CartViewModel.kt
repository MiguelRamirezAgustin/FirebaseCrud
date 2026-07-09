package com.example.crudfirebase.appFirebase.ui.viewModelShopping

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.ui.views.product.model.CartItemSelect
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cart = MutableStateFlow<List<CartItemSelect>>(emptyList())
    val cart = _cart.asStateFlow()


    fun addProduct(product: ProductModel) {

        val list = _cart.value.toMutableList()

        val index = list.indexOfFirst {
            it.product.id == product.id
        }

        if (index >= 0) {

            val item = list[index]

            list[index] = item.copy(
                quantity = item.quantity + 1
            )

        } else {

            list.add(
                CartItemSelect(product)
            )

        }

        _cart.value = list
    }


    fun removeProduct(product: ProductModel) {

        val list = _cart.value.toMutableList()

        val index = list.indexOfFirst {
            it.product.id == product.id
        }


        if(index >= 0){

            val item = list[index]

            if(item.quantity > 1){

                list[index] = item.copy(
                    quantity = item.quantity - 1
                )

            }else{

                list.removeAt(index)

            }
        }

        _cart.value = list
    }


    fun total(): Double {

        return _cart.value.sumOf {
            it.product.price * it.quantity
        }

    }


    fun clear(){
        _cart.value = emptyList()
    }
}