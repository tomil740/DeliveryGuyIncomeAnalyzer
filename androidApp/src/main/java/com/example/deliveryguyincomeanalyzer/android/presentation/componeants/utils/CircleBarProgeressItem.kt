package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.CircleValues
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay


@Composable
fun CircleProgressItem(
    valueHeader: String,
    barSize : Float,
    barValue : Float,
    barColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    valueColor: Color = MaterialTheme.colorScheme.secondaryContainer ,
    textColor:Color =MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier
) {


    val valueAnimationState = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = barSize) {
        valueAnimationState.animateTo(
            targetValue = (barValue / barSize),
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }

    var itemSizeState by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.weight(1f)) {

            Box(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .onGloballyPositioned {
                            itemSizeState = with(density) {
                                it.size.height.toDp()
                            }
                        },
                ) {
                    drawArc(
                        color = barColor,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = 22.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawArc(
                        color = valueColor,
                        startAngle = -90f,
                        sweepAngle = 360f *valueAnimationState.value,
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = 22.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemSizeState),
                    contentAlignment = Alignment.Center
                ) {

                    CircleValues(
                        theHeight = itemSizeState,
                        barSize,
                        barValue,
                        textStyle = MaterialTheme.typography.displayMedium,
                        textColor=textColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier) {

            Text(
                text = valueHeader,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

        }
        
    }


}