package com.example.crudfirebase.appFirebase.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SlideToConfirmButton(
    text: String = "CONTINUAR",
    enabled: Boolean = true,
    onComplete: () -> Unit
) {

    val maxWidthPx = remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    val thumbSize = 56.dp

    val offsetX = remember { mutableStateOf(0f) }
    val showThumb = remember { mutableStateOf(false) }
    val isAnimating = remember { mutableStateOf(false) }

    val animatedOffset = animateFloatAsState(
        targetValue = offsetX.value,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    fun startAnimation() {
        if (isAnimating.value || !enabled) return

        isAnimating.value = true
        showThumb.value = true

        val maxOffset =
            maxWidthPx.value - with(density) { thumbSize.toPx() }

        kotlinx.coroutines.MainScope().launch {
            delay(150)
            offsetX.value = maxOffset
            delay(750)
            onComplete()
            offsetX.value = 0f
            showThumb.value = false
            isAnimating.value = false
        }
    }

    val backgroundColor = if (enabled) color_blue else Color.White
    val borderColor = if (enabled) Color.Transparent else color_blue
    val textColor = if (enabled) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .onGloballyPositioned {
                maxWidthPx.value = it.size.width.toFloat()
            }
            .clickable(enabled = enabled && !isAnimating.value) {
                startAnimation()
            }
    ) {

        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        if (showThumb.value) {

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(animatedOffset.value.toInt(), 0)
                    }
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.CenterStart),
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
}