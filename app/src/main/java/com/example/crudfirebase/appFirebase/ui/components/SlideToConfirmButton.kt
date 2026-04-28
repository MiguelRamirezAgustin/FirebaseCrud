package com.example.crudfirebase.appFirebase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebase.ui.theme.color_blue
import com.example.crudfirebase.ui.theme.color_red
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun SlideToConfirmButton(
    text: String = "SAVE",
    onComplete: () -> Unit
) {
    val maxWidthPx = remember { mutableStateOf(0f) }
    val offsetX = remember { mutableStateOf(0f) }

    val thumbSize = 56.dp
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(50))
            .background(color_blue)
            .onGloballyPositioned {
                maxWidthPx.value = it.size.width.toFloat()
            }
    ) {

        Text(
            text = text,
            color = color_write,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(offsetX.value.toInt(), 0)
                }
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterStart)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val maxOffset = maxWidthPx.value - with(density) { thumbSize.toPx() }

                        offsetX.value = (offsetX.value + delta)
                            .coerceIn(0f, maxOffset)
                    },
                    onDragStopped = {
                        val maxOffset = maxWidthPx.value - with(density) { thumbSize.toPx() }

                        if (offsetX.value > maxOffset * 0.8f) {
                            offsetX.value = maxOffset
                            onComplete()
                        } else {
                            offsetX.value = 0f
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = color_blue
            )
        }
    }
}