package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleValues(theHeight:Dp,barSize:Float,barValue:Float,textStyle: TextStyle = MaterialTheme.typography.displayLarge,
                 textColor:Color=MaterialTheme.colorScheme.onPrimary
) {

    Box(
        modifier = Modifier
            .height(theHeight)
            .offset(x = -22.dp,y=-4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(modifier = Modifier.rotate(-9f).offset(x = 12.dp)) {
                UnitDisplay(
                    amount = barValue,
                    unitIcon = Icons.Default.Star,//"hours /",//stringResource(id = R.string.grams),
                    amountColor = textColor,
                    unitColor = textColor,
                    amountTextStyle = MaterialTheme.typography.displaySmall
                )
            }

            Box(modifier = Modifier.offset(x = 44.dp)) {
                UnitDisplay(
                    amount = barSize,
                    unitIcon = Icons.Default.Star,//stringResource(id = R.string.grams),
                    amountColor = textColor,
                    unitColor = textColor,
                    amountTextStyle = MaterialTheme.typography.titleLarge
                )
            }
        }

        Text(
            text = " | ",
            color = Color.Black,
            style = textStyle,
            modifier = Modifier
                .offset(x = 24.dp)
                .rotate(78f)
        )



    }
}