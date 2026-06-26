package com.example.crudfirebase.appFirebase.data.repository

import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveProduct(
        product: ProductModel,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("products")
            .document(product.id)
            .set(product)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getProducts(
        onSuccess: (List<ProductModel>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("products")
            .get()
            .addOnSuccessListener { result ->

                val products =
                    result.toObjects(ProductModel::class.java)

                onSuccess(products)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
}