package com.example.crudfirebase.appFirebase.data.repository


import android.util.Log
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject


class OrderRepository @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()


    /**
     * Crear pedido
     */
    fun createOrder(
        order: OrderModel,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val document = firestore
            .collection("orders")
            .document()


        val newOrder = order.copy(
            orderId = document.id
        )

        document
            .set(newOrder)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }


    /**
     * Obtener pedidos
     */
    fun getOrders(
        result: (List<OrderModel>) -> Unit
    ) {
        firestore.collection("orders")
            .get()
            .addOnSuccessListener { snapshot ->
                val orders =
                    snapshot.documents.mapNotNull { document ->
                        document.toObject(
                            OrderModel::class.java
                        )
                    }
                result(orders)
            }
            .addOnFailureListener {
                Log.e("ORDER_REPOSITORY", it.message ?: "Error")
            }
    }
}