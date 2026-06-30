package com.example.crudfirebase.appFirebase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebase.R
import com.example.crudfirebase.ui.theme.color_black
import com.example.crudfirebase.ui.theme.color_blue
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Contraseña",
) {

    val isFocused = remember { mutableStateOf(false) }
    val isVisible = remember { mutableStateOf(false) }

    val borderColor = when {
        isFocused.value -> Color.White
        else -> Color.White.copy(alpha = 0.35f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.18f))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = Color.White.copy(alpha = .85f),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= 20) {
                    onValueChange(it)
                }
            },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            visualTransformation =
                if (isVisible.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            decorationBox = { innerTextField ->

                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.White.copy(alpha = 0.55f),
                        fontSize = 15.sp
                    )
                }

                innerTextField()
            }
        )

        Icon(
            painter = painterResource(
                if (isVisible.value)
                    R.drawable.visibility
                else
                    R.drawable.eye
            ),
            contentDescription = null,
            tint = Color.White.copy(alpha = .75f),
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    isVisible.value = !isVisible.value
                }
        )
    }
}