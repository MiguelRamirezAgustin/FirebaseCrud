package com.example.crudfirebase.appFirebase.ui.views.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.appFirebase.ui.views.base64ToBitmap
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import com.example.crudfirebase.appFirebase.viewmodel.ProductViewModel
import com.example.crudfirebase.ui.theme.ColorRojoFondoX
import com.example.crudfirebase.ui.theme.ColorRojoIconoX
import com.example.crudfirebase.ui.theme.FondoBot
import com.example.crudfirebase.ui.theme.FondoTop
import com.example.crudfirebase.ui.theme.FondoTopProduct
import com.example.crudfirebase.ui.theme.color_write


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListAdmin(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
) {

    val products = viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "     Lista de productos ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FondoTopProduct
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddProductScreen.route)
                },
                containerColor = Color(0xFF1E293B),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.border(
                    width = 2.dp,
                    color = Color(0xFF4ADE80),
                    shape = RoundedCornerShape(16.dp)
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null,
                    modifier = Modifier,
                    tint = Color.White
                )
            }
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF143F46), Color(0xFF161A22))
                        )
                    ),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products.value) { product ->
                    ProductItem(
                        product = product,
                        showDelete = true,
                        onDeleteClick = {
                            viewModel.deleteProduct(product.id)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ProductItem(
    product: ProductModel,
    showDelete: Boolean = false,
    onDeleteClick: (() -> Unit)? = null,
    showAddCart: Boolean = false,
    onAddCart: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF282C37).copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E222B)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = base64ToBitmap(product.imageUrl).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = color_write,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción corta en gris atenuado
            Text(
                text = product.description,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF94A3B8),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "$${product.price}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF4ADE80)
            )

            Spacer(modifier = Modifier.height(5.dp))
        }
        if (showAddCart && onAddCart != null) {

            Button(
                onClick = onAddCart,
                modifier = Modifier.fillMaxWidth().height(40.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4ADE80),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Agregar")
            }
        }
        if (showDelete && onDeleteClick != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .background(ColorRojoFondoX)
                        .clickable { onDeleteClick() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Eliminar",
                        tint = ColorRojoIconoX,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}