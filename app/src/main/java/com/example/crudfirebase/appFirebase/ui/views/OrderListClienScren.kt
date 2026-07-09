package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderModel
import com.example.crudfirebase.appFirebase.ui.views.product.model.OrderStatus
import com.example.crudfirebase.appFirebase.viewmodel.ProductOrderViewModel
import com.example.crudfirebase.ui.theme.FondoBot
import com.example.crudfirebase.ui.theme.FondoTop
import com.example.crudfirebase.ui.theme.FondoTopProduct
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListClienScren( navController: NavController,
                         viewModel: ProductOrderViewModel = hiltViewModel()
) {
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val orders = viewModel.orders.collectAsState()
    LaunchedEffect(Unit) {
        firebaseUser?.uid?.let {
            viewModel.getOrdersByUser(it)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Mis pedidos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FondoTopProduct
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(FondoTop, FondoBot)
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(orders.value) { item ->
                    showOrderclient(
                        item = item,
                        viewModel = viewModel
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}


@Composable
fun showOrderclient( item: OrderModel,
                    viewModel: ProductOrderViewModel) {
    val colorVerdeNeon = Color(0xFF4ADE80)
    val colorFondoTarjeta = Color(0xFF1E293B).copy(alpha = 0.85f)
    val colorTextoSecundario = Color(0xFF94A3B8)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorFondoTarjeta
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.customerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Text(
                    text = item.status,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = statusColor(item.status),
                    modifier = Modifier
                        .background(
                            color = colorVerdeNeon.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = "Tel: ${item.customerPhone}  •  ${item.customerEmail}",
                fontSize = 13.sp,
                color = colorTextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "Productos",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = colorTextoSecundario
            )

            Spacer(modifier = Modifier.height(6.dp))

            item.products.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.productName,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "x${product.quantity}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = colorVerdeNeon,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Fila de Cierre con el Monto Total de Pago
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total a Pagar",
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = Color.White
                )
                Text(
                    text = "$${item.total}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorVerdeNeon
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (item.status == OrderStatus.PENDING) {

                Button(
                    onClick = {

                        viewModel.updateOrderStatus(
                            orderId = item.orderId,
                            status = OrderStatus.CANCELLED,
                            onSuccess = {
                                viewModel.getOrdersByUser(item.userId)
                            },
                            onError = {

                            }
                        )

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .height(45.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Cancelar pedido",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    text = when (item.status) {
                        OrderStatus.PROCESSING ->
                            "Tu pedido está siendo preparado."
                        OrderStatus.READY ->
                            "Tu pedido está listo."
                        OrderStatus.DELIVERED ->
                            "Tu pedido fue entregado."
                        OrderStatus.CANCELLED ->
                            "Este pedido fue cancelado."
                        else -> ""
                    },
                    color = statusColor(item.status),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

            }
        }
    }
}




