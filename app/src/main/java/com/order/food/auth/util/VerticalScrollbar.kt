package com.order.food.auth.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalScrollbar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    width: Dp = 4.dp,
    color: Color = Color.Gray.copy(alpha = 0.6f)
) {

    val alpha by animateFloatAsState(
        targetValue = if (scrollState.isScrollInProgress) 1f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
            .alpha(alpha)
    ) {

        val viewportHeight = size.height

        val contentHeight = viewportHeight + scrollState.maxValue

        if (contentHeight <= viewportHeight) return@Canvas

        val thumbHeight =
            (viewportHeight * viewportHeight / contentHeight)
                .coerceAtLeast(40.dp.toPx())

        val thumbOffset =
            (scrollState.value.toFloat() / scrollState.maxValue) *
                    (viewportHeight - thumbHeight)

        drawRoundRect(
            color = color,
            topLeft = Offset(0f, thumbOffset),
            size = Size(size.width, thumbHeight),
            cornerRadius = CornerRadius(50f, 50f)
        )
    }
}