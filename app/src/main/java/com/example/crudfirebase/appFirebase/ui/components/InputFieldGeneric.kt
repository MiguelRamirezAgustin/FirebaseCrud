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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebase.ui.theme.color_black
import com.example.crudfirebase.ui.theme.color_blue
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun InputFieldGeneric(
    maxDigit: Int,
    typeKeyboardType: KeyboardType,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Correo electronico.",
    isFocused: Boolean = false,
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {

    val borderColor = when {
        isError -> Color.Red
        isFocused -> Color.White
        else -> Color.White.copy(alpha = 0.35f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(
                1.dp,
                borderColor,
                RoundedCornerShape(14.dp)
            )
            .background(
                Color.White.copy(alpha = 0.18f)
            )
            .clickable {
                onClick?.invoke()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= maxDigit) {
                    onValueChange(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            readOnly = isReadOnly,
            cursorBrush = SolidColor(Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = typeKeyboardType,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                textDecoration = TextDecoration.None
            ),
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
    }
}