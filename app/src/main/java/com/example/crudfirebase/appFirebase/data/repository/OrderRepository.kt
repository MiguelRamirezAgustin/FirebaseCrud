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
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    Log.e("ORDER_REPOSITORY", error.message ?: "Error")
                    return@addSnapshotListener
                }

                val orders = snapshot?.documents?.mapNotNull {
                    it.toObject(OrderModel::class.java)
                }.orEmpty()

                result(orders)
            }

    }



    /**Add new status order*/
    fun updateOrderStatus(
        orderId: String,
        status: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection("orders")
            .document(orderId)
            .update(
                "status",
                status
            )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }


    /**Get order cliente*/
    fun getOrdersByUser(
        userId: String,
        result: (List<OrderModel>) -> Unit
    ) {
        firestore.collection("orders")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    Log.e("ORDER_REPOSITORY", error.message ?: "Error")
                    return@addSnapshotListener
                }

                val orders = snapshot?.documents?.mapNotNull {
                    it.toObject(OrderModel::class.java)
                }.orEmpty()

                result(orders)
            }
    }
}