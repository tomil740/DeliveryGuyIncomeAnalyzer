package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import android.icu.text.ListFormatter
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    weekTarget : Float,
    value : Float,
    barColor : Color = MaterialTheme.colorScheme.onPrimaryContainer,
    valueColor : Color = MaterialTheme.colorScheme.primaryContainer,
    onItemClick: ()->Unit,
    modifier: Modifier
) {



    val valueAnimationState = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = value, key2 = weekTarget) {
        valueAnimationState.animateTo(
            targetValue = if (weekTarget > 0) {
                value / weekTarget
            } else 0f,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }

    Canvas(
        modifier = modifier.clickable { onItemClick() }
    ) {
        drawRoundRect(
            color =  barColor,
            size = size,
            cornerRadius = CornerRadius(100f)
        )
        drawRoundRect(
            color =  valueColor,
            size = Size(
                width = (valueAnimationState.value)*size.width,
                height = size.height
            ),
            cornerRadius = CornerRadius(100f)
        )
    }





}