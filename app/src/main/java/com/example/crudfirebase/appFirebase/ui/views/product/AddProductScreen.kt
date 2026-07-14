package com.example.crudfirebase.appFirebase.ui.views.product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.crudfirebase.appFirebase.ui.components.ImageUtils.uriToBase64
import com.example.crudfirebase.appFirebase.ui.views.product.model.ProductModel
import com.example.crudfirebase.appFirebase.viewmodel.ProductViewModel
import com.example.crudfirebase.ui.theme.BordeEnfoque
import com.example.crudfirebase.ui.theme.BordeNormal
import com.example.crudfirebase.ui.theme.FondoBotProduct
import com.example.crudfirebase.ui.theme.FondoTopProduct
import com.example.crudfirebase.ui.theme.InputFondo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    val message = viewModel.message.collectAsState()

    var name = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }
    var price = remember { mutableStateOf("") }


    var imageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    var imageBase64 = remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri.value = uri

        uri?.let {
            imageBase64.value = uriToBase64(context, it)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Registrar producto",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
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
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(FondoTopProduct, FondoBotProduct)
                        )
                    )
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4ADE80),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            launcher.launch("image/*")
                        }
                    ) {
                        Text("Seleccionar imagen")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    imageUri.value?.let { uri ->

                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Nombre del artículo") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InputFondo,
                            unfocusedContainerColor = InputFondo,
                            focusedBorderColor = BordeEnfoque,
                            unfocusedBorderColor = BordeNormal,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color.LightGray,
                            unfocusedLabelColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text("Descripción") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InputFondo,
                            unfocusedContainerColor = InputFondo,
                            focusedBorderColor = BordeEnfoque,
                            unfocusedBorderColor = BordeNormal,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color.LightGray,
                            unfocusedLabelColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    OutlinedTextField(
                        value = price.value,
                        onValueChange = { price.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Precio ($)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InputFondo,
                            unfocusedContainerColor = InputFondo,
                            focusedBorderColor = BordeEnfoque,
                            unfocusedBorderColor = BordeNormal,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color.LightGray,
                            unfocusedLabelColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val product = ProductModel(
                                name = name.value,
                                description = description.value,
                                price = price.value.toDoubleOrNull() ?: 0.0,
                                imageUrl = imageBase64.value
                            )
                            viewModel.addProduct(product)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = FondoBotProduct
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (message.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = message.value,
                            color = Color(0xFF94A3B8),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
