package com.example.crudfirebase.appFirebase.data.repository

import android.net.Uri
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun addProduct(
        product: ProductModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val document = firestore.collection("products").document()
        val newProduct = product.copy(id = document.id)

        document
            .set(newProduct)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getProductsListener(
        onData: (List<ProductModel>) -> Unit,
        onError: (Exception) -> Unit
    ) {

        firestore.collection("products")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                val list = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductModel::class.java)
                } ?: emptyList()

                onData(list)
            }
    }

    fun deleteProduct(
        productId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FirebaseFirestore.getInstance()
            .collection("products")
            .document(productId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}