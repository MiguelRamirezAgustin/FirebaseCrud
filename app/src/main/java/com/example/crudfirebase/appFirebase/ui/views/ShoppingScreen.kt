package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import com.example.crudfirebase.appFirebase.ui.viewModelShopping.CartViewModel

@Composable
fun ShoppingScreen(navController: NavHostController,
    cartViewModel: CartViewModel = viewModel()
    ) {

        val cart = cartViewModel.cart.collectAsState()
    Log.d("Print Log ========>", " ShoppingScreen::${cart}")
        LazyColumn {
            items(cart.value) { item ->
                Text(item.product.name)
                Text("$${item.product.price}")
                Text("Cantidad: ${item.quantity}")
            }
        }
    }