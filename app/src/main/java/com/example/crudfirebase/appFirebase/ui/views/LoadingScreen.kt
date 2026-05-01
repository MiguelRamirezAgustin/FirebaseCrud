package com.example.crudfirebase.appFirebase.ui.views
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.example.crudfirebase.ui.theme.color_blue

@Composable
fun LoadingScreen() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.5f)
            ),
        color = Color.Transparent
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Spacer(modifier = Modifier.height(20.dp))
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Cargando...",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = color_blue
                    )
                }
            }
        }
    }
}