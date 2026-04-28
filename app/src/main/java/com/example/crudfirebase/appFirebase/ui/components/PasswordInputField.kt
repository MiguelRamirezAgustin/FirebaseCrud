package com.example.crudfirebase.appFirebase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebase.ui.theme.color_black
import com.example.crudfirebase.ui.theme.color_blue
import com.example.crudfirebase.ui.theme.color_red
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter your password",
) {

    var isFocused = remember { mutableStateOf(false) }
    var isVisible = remember { mutableStateOf(false) }

    val borderColor = when {
        isFocused.value -> color_black
        else -> color_black
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .background(color_write)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "password icon",
            tint = color_blue,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontSize = 16.sp
            ),
            visualTransformation = if (isVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )


        Icon(
            imageVector = if (isVisible.value)
                Icons.Default.Check
            else
                Icons.Default.KeyboardArrowUp,
            contentDescription = "toggle password",
            tint = Color.Gray,
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    isVisible.value = !isVisible.value
                }
        )
    }
}