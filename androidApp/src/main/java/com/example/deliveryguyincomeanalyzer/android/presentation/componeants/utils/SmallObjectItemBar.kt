package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size

@Composable
fun SmallObjectItemBar(barSize : Float , barValue : Float ,modifier: Modifier) {
    Box(modifier = modifier.rotate(270f)){

        val backroundColor = MaterialTheme.colorScheme.secondaryContainer
        val color = MaterialTheme.colorScheme.primary

        Canvas(modifier = modifier)
        {

            drawRoundRect(
                color =  backroundColor,
                size = size,
                cornerRadius = CornerRadius(100f,y=100f)
            )
            drawRoundRect(
                color =  color,
                size = Size(
                    width = 0.5f*size.width,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f,y=0f)
            )


            /*
            drawRoundRect(color = backroundColor,size = Size(size.width,size.height),cornerRadius = CornerRadius(100f),
            )
            drawRoundRect(color =  color, size = Size(
                width = size.width/3f,//(currentVal/maxVal)
                height = size.height), topLeft = Offset(y=70f,x=0f), cornerRadius = CornerRadius(100f)
            )

             */
        }

    }

}