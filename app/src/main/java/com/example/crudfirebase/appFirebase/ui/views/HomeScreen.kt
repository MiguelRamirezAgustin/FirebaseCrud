package com.example.crudfirebase.appFirebase.ui.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FormatListNumberedRtl
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.appFirebase.ui.views.product.ProductItem
import com.example.crudfirebase.appFirebase.ui.viewModelShopping.CartViewModel
import com.example.crudfirebase.appFirebase.viewmodel.ProductViewModel
import com.example.crudfirebase.ui.theme.FondoBot
import com.example.crudfirebase.ui.theme.FondoTop
import com.example.crudfirebase.ui.theme.FondoTopProduct
import com.example.crudfirebase.ui.theme.color_write
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController,
               viewModel: ProductViewModel = viewModel(),
               cartViewModel: CartViewModel) {

    val uid = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid
    val cart = cartViewModel.cart.collectAsState()
    var userLogin = remember { mutableStateOf<UserModel?>(null) }
    val products = viewModel.products.collectAsState()
    LaunchedEffect(Unit) {

        Log.d("HOME", "UID: $uid")

        if (uid == null) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
            return@LaunchedEffect
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                userLogin.value =
                    document.toObject(UserModel::class.java)
            }
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.85f),
                drawerContainerColor = Color(0xFF161A22)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Image(
                        painter = painterResource(R.drawable.burgericon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tarjeta del usuario
                    CardUser(userLogin.value)

                    Spacer(modifier = Modifier.height(20.dp))

                    DrawerItemCard(
                        title = "Ver perfil",
                        icon = Icons.Default.Person
                    ) {
                        navController.navigate(Screen.ProfileUserScreeInfo.route)
                    }

                    DrawerItemCard(
                        title = "Actualizar información",
                        icon = Icons.Default.Edit
                    ) {
                        navController.navigate(Screen.EditProfileUserScreen.route)
                    }


                    if (userLogin.value?.admin == true) {


                        DrawerItemCard(
                            title = "Pedidos",
                            icon = Icons.Default.Book
                        ) {
                            navController.navigate(Screen.AdminOrdersScreen.route)
                        }

                        DrawerItemCard(
                            title = "Mis productos",
                            icon = Icons.Default.ShoppingCart
                        ) {
                            navController.navigate(Screen.ProductListAdmin.route)
                        }

                        DrawerItemCard(
                            title = "Registrar Producto",
                            icon = Icons.Default.Add
                        ) {
                            navController.navigate(Screen.AddProductScreen.route)
                        }

                        DrawerItemCard(
                            title = "Administrar usuarios",
                            icon = Icons.Default.AccountCircle
                        ) {
                            navController.navigate(Screen.ListUserScreen.route)
                        }
                    }
                    DrawerItemCard(
                        title = "Mis pedidos",
                        icon = Icons.Default.FormatListNumberedRtl
                    ) {
                        navController.navigate(Screen.OrderListClienScren.route)
                    }
                    DrawerItemCard(
                        title = "Cerrar sesión",
                        icon = Icons.Default.ExitToApp,
                        iconTint = Color.Red,
                        textColor = Color.Red
                    ) {

                        FirebaseAuth.getInstance().signOut()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Hola ${userLogin.value?.name}")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = FondoTopProduct
                    ),
                    actions = {

                        IconButton(
                            onClick = {
                                val name = userLogin.value?.name
                                val mail = userLogin.value?.email
                                val phone = userLogin.value?.phone
                                navController.navigate(Screen.ShoppingScreen.route +"/$name/$phone/$mail ")
                            }
                        ) {

                            BadgedBox(
                                badge = {
                                    if (cart.value.isNotEmpty()) {
                                        Badge {
                                            Text(cart.value.size.toString())
                                        }
                                    }
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito"
                                )
                            }
                        }
                    }
                )
            },

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
                                colors = listOf(Color(0xFF143F46), Color(0xFF143F46))
                            )
                        ),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products.value) { product ->
                        ProductItem(
                            product = product,
                            showDelete = false,
                            showAddCart = true,
                            onAddCart = {
                                cartViewModel.addProduct(product)
                            }
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun CardUser(
    user: UserModel?
) {

   Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.Person4,
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = user?.name ?: "",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun DrawerItemCard(
    title: String,
    icon: ImageVector,
    iconTint: Color = color_write,
    textColor: Color = color_write,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


fun base64ToBitmap(base64: String): Bitmap {
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(
        bytes,
        0,
        bytes.size
    )
}