package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


/*
ShiftsDivisionBar:

the values will be in growing order what means always the higher number will be bigger value
the startValue state is the start of the morning shift(the first of the day) , in order to skip the un used start


 */
@Composable
fun ShiftsDivisionBar(
    barSize : Int =24,
    startValue:Float=0.123f,
    value1 : Float=0.205f,
    value2 : Float=0.451f,
    value3 : Float=0f,
    barColor : Color = Color.Gray,
    value1Color : Color = Color.Blue,
    value2Color : Color = Color.Red,
    value3Color : Color =Color.Yellow,
    modifier: Modifier
) {

    val start = remember {
        mutableStateOf(0f)
    }
    val valueAnimationState3 = remember {
        Animatable(0f)
    }
    val valueAnimationState2 = remember {
        Animatable(0f)
    }
    val valueAnimationState1 = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = value1, key2 = barSize, key3 = value2) {
        valueAnimationState1.animateTo(
            targetValue = if (barSize > 0) {
                start.value = (barSize/24)*startValue
                (value1* (barSize/24))+start.value
            } else 0f,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
        valueAnimationState2.animateTo(
            targetValue = if (barSize > 0) {
                (value2* (barSize/24))+start.value
            } else 0f,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
        valueAnimationState3.animateTo(
            targetValue = if (barSize > 0) {
                ((barSize/24)*value3)+start.value
            } else 0f,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }

    val density = LocalDensity.current
    var theBarSize by remember { mutableStateOf(0.dp) }
Box() {
    Canvas(
        modifier = modifier.clickable { }.onGloballyPositioned {
            theBarSize = with(density) {
                it.size.width.toDp()
            }
        }
    ) {
        drawRoundRect(
            color = barColor,
            size = size,
            cornerRadius= CornerRadius(100f)
        )
        drawRect(
            color = value3Color,
            size = Size(
                width = (valueAnimationState3.value) * size.width,
                height = size.height
            ),
        )
        drawRect(
            color = value2Color,
            size = Size(
                width = (valueAnimationState2.value) * size.width,
                height = size.height
            ),
        )
        drawRect(
            color = value1Color,
            size = Size(
                width = (valueAnimationState1.value) * size.width,
                height = size.height
            ),
        )
        drawRoundRect(
            color = barColor,
            size = Size(
                width = (startValue) * size.width,
                height = size.height
            ),
            cornerRadius= CornerRadius(x=100f,y=0f)
        )
    }

    for (i in 0..8){
        var theText = "${(i+2)*3}"
        if(i == 6)
            theText="00"

        if (i==7)
            theText="3"

        Text(text = ".",Modifier.align(Alignment.BottomStart).offset(x=theBarSize*0.125f*(i),y=4.dp), style = MaterialTheme.typography.titleLarge)
        Text(text = theText,Modifier.align(Alignment.BottomStart).offset(x=theBarSize*0.125f*(i),y=24.dp), style = MaterialTheme.typography.titleSmall)
    }


}


}