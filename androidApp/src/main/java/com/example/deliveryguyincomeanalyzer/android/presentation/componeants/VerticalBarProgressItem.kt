package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import com.example.deliveryguyincomeanalyzer.android.presentation.core.valuePresentation

@Composable
fun VerticalBarProgressItem(barValue: Float,comparableValue:Float,modifier: Modifier,
                            barColor : Color = MaterialTheme.colorScheme.onPrimaryContainer, valueColor : Color = MaterialTheme.colorScheme.primaryContainer,textColor:Color = MaterialTheme.colorScheme.secondary,
                            attributeName:String="Extras") {

    //with rotate attribute we will stay with the old preRotate dimensions
    //because of that we will apply matched offSet value to get the new start point of the object
    //the value will be base form half of the original axis size



        Box(modifier = Modifier.height(400.dp).offset(y=50.dp), contentAlignment = Alignment.BottomCenter) {
            Column(modifier.offset(x=5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            UnitDisplay(amount = barValue, unitIcon = Icons.Default.Notifications,amountTextStyle=MaterialTheme.typography.titleLarge)
               Text(text = attributeName, style = MaterialTheme.typography.titleLarge)
                
            }
        }
        ProgressBar(
            weekTarget = comparableValue,
            value = barValue,
            onItemClick = { /*TODO*/ },
            barColor = barColor,
            valueColor = valueColor,
            modifier = modifier.rotate(-90f)
        )

        Box(modifier = Modifier.height(400.dp).offset(y=-20.dp), contentAlignment = Alignment.TopCenter) {
            Column(modifier.offset(x = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                UnitDisplay(
                    amount = comparableValue,
                    unitIcon = Icons.Default.Star,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    amountColor = textColor
                )
            }
        }


}