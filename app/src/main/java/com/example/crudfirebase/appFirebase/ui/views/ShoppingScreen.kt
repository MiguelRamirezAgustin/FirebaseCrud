package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crudfirebase.R

import com.example.crudfirebase.appFirebase.ui.viewModelShopping.CartViewModel
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderModel
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderProduct
import com.example.crudfirebase.appFirebase.viewmodel.ProductOrderViewModel


import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    name: String? = "",
    phone: String? = "",
    email: String? = "",
    viewModel: ProductOrderViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val cart = cartViewModel.cart.collectAsState()
    LaunchedEffect(cart.value) {
        Log.d("SHOPPING", cart.value.toString())
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.confirm_Order),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            null,
                            tint = Color.White
                        )
                    }
                },
                actions = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF103E43)
                )
            )
        },
        bottomBar = {

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF212B36)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = stringResource(id = R.string.total_to_Pay),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        "$${cartViewModel.total()}",
                        color = Color(0xFF4ADE80),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                }

                Button(
                    onClick = {
                        val productsOrder = cart.value.map {
                            OrderProduct(
                                productId = it.product.id,
                                productName = it.product.name,
                                price = it.product.price,
                                quantity = it.quantity
                            )

                        }

                        val order = OrderModel(
                            userId = FirebaseAuth.getInstance()
                                .currentUser
                                ?.uid.orEmpty(),
                            customerName = name.toString(),
                            customerPhone = phone.toString(),
                            customerEmail = email.toString(),
                            products = productsOrder,
                            total = productsOrder.sumOf {
                                it.price * it.quantity
                            },
                            status = "Pendiente",
                            date = System.currentTimeMillis()
                        )

                        viewModel.createOrder(
                            order = order,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Pedido realizado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                cartViewModel.clear()

                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                     "Ocurrió un error",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4ADE80)
                    )
                ) {

                    Text(
                        text = stringResource(id = R.string.place_Order),
                        color = Color.Black
                    )
                }
                Spacer(Modifier.height(25.dp))
            }
        },
        containerColor = Color(0xFF14343B)
    ) { padding ->

        Card(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF212B36)
            )
        ) {

            Text(
                text = stringResource(id = R.string.purchase_Summary),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {

                items(cart.value) { item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            bitmap = base64ToBitmap(item.product.imageUrl).asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                item.product.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                "$${item.product.price}",
                                color = Color(0xFF4ADE80)
                            )
                        }

                        Text(
                            text = "x ${item.quantity}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = {
                                cartViewModel.removeProduct(item.product)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color(0xFF4ADE80)
                            )
                        }
                    }


                    HorizontalDivider(
                        color = Color(0xFF35505A)
                    )
                }
            }
        }
    }
}