package com.silverorange.videoplayer.feature.videolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silverorange.videoplayer.R

@Composable
fun VideoControllers(
    modifier: Modifier = Modifier,
    isPaused: State<Boolean>,
    onPlayPauseClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier.background(Color.Black.copy(alpha = 0.5f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Controller(
            drawableId = R.drawable.previous,
            contentDescription = "Previous",
            size = 48.dp,
            onClick = onPreviousClick
        )

        Controller(
            drawableId = if (isPaused.value) R.drawable.play else R.drawable.pause,
            contentDescription = "Play",
            size = 56.dp,
            onClick = onPlayPauseClick
        )

        Controller(
            drawableId = R.drawable.next,
            contentDescription = "Next",
            size = 48.dp,
            onClick = onNextClick
        )
    }
}

@Composable
fun Controller(
    drawableId: Int,
    contentDescription: String,
    size: Dp,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(CircleShape)
        .background(Color.White)
        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
        .padding(8.dp)
        .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = contentDescription,
            Modifier.size(size)
        )
    }
}