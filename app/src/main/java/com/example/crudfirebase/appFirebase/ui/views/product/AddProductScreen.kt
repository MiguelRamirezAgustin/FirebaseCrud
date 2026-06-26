package com.example.crudfirebase.appFirebase.ui.views.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.ui.components.GenericInputField
import com.example.crudfirebase.appFirebase.ui.components.GenericInputFieldProduct
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {

    var name = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }
    var price = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

           Column( modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 24.dp)) {

               GenericInputFieldProduct(
                   value = name.value,
                   onValueChange = {  name.value = it },
                   placeholder = "Nombre",
                   isFocused = false,
                   isError = false
               )

               Spacer(modifier = Modifier.height(12.dp))



               GenericInputFieldProduct(
                   value = description.value,
                   onValueChange = {  description.value = it },
                   placeholder = "Descripción",
                   isFocused = false,
                   isError = false
               )

               Spacer(modifier = Modifier.height(12.dp))


               GenericInputFieldProduct(
                   value = price.value,
                   onValueChange = {  price.value = it },
                   placeholder = "Precio",
                   isFocused = false,
                   isError = false
               )

               Spacer(modifier = Modifier.height(24.dp))


               SlideToConfirmButton(
                   text = stringResource(id = R.string.text_save),
                   enabled = true,
                   onComplete = {
                       viewModel.saveProduct(
                           name = name.value,
                           description = description.value,
                           price = price.value.toDoubleOrNull() ?: 0.0
                       ) {
                           navController.popBackStack()
                       }
                   }
               )
           }
        }
    }
}