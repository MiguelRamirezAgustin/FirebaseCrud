package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.ui.theme.color_blue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val uid = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid

    var userLogin = remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(Unit) {

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener { document ->
                Log.d("", "User:: ${document}")
                userLogin.value = document.toObject(UserModel::class.java)
                Log.d("", "User:: ${userLogin.value}")
            }
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.iconfirebase),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "${userLogin.value?.name}",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                NavigationDrawerItem(
                    label = {
                        Text(
                            "Perfil",
                            fontWeight = FontWeight.Medium
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(Screen.ProfileUserScreenInfo.route)
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = color_blue,
                        )
                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text(
                            "Editar perfil",
                            fontWeight = FontWeight.Medium
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(Screen.EditProfileUserScreen.route)
                    },
                    icon = {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            tint = color_blue,
                        )
                    }
                )

                if (userLogin.value?.admin == true) {
                    NavigationDrawerItem(
                        label = {
                            Text(
                                "Administrar perfil",
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.ListUserScreen.route)
                        },
                        icon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = color_blue,
                            )
                        }
                    )
                }

                NavigationDrawerItem(
                    label = {
                        Text(
                            "Cerrar sesión",
                            fontWeight = FontWeight.Medium
                        )
                    },
                    selected = false,
                    onClick = {
                        FirebaseAuth
                            .getInstance()
                            .signOut()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = color_blue,
                        )
                    }
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Hola ${userLogin.value?.name}",
                            fontWeight = FontWeight.Medium
                        )
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
                    }
                )
            }

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }
}