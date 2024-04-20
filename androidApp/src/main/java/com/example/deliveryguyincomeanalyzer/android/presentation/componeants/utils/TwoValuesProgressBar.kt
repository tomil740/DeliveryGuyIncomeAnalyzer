package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ExpandedDataItem
import java.math.RoundingMode

/*
This function will get as parameters all of the wanted attribute to be present on the graph
and the matched color (for maximum abstraction), the state of the switchable graph will be host inside
this function
 */

@Composable
fun TwoValuesProgressBar(barVal:Float,comparableVal:Float,subBarVal:Float,subComparableVal:Float,
                    isFullBar : Boolean = true,perHourValue:Float = 0f,perHourComparable:Float = 0f,
                         perDeliveryValue:Float = 0f,perDeliveryComparable:Float = 0f,
                         perSessionValue:Float = 0f,perSessionComparable:Float = 0f,
                         modifier: Modifier = Modifier
) {
    var barpouration =0.9f
    var isDefaultBar by remember { mutableStateOf(false) }
    var isExpandet by remember { mutableStateOf(false) }

    var barSize by remember { mutableStateOf(comparableVal) }
    var barValue by remember { mutableStateOf(barVal) }
    var subValue by remember { mutableStateOf(subBarVal) }
    var subSize by remember { mutableStateOf(subComparableVal) }

    LaunchedEffect(key1 = comparableVal , key2 = barVal) {
        barSize = comparableVal
        barValue= barVal
        subValue = subBarVal
        subSize= subComparableVal
    }

    if (isFullBar)
        barpouration=1f

    LaunchedEffect(key1 = isDefaultBar) {
        val a = barSize
        val b = barValue
        barSize = subSize
        barValue = subValue
        subValue = b
        subSize = a
    }
    val density = LocalDensity.current
    var itemSize by remember { mutableStateOf(0.dp) }

    Box(modifier = modifier
        .fillMaxWidth()) {

        Column(modifier = Modifier
            .fillMaxWidth(barpouration)
            .offset(y = -16.dp)
            .onGloballyPositioned {
                itemSize = with(density) {
                    it.size.height.toDp()
                }
            }, horizontalAlignment = Alignment.Start) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //as for now I pass this behaviour to the unit display , at future probably will nav to the specific value analytics
                UnitDisplay(
                    amount = barValue.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = barSize.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.Notifications,
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 42.dp, end = 42.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnitDisplay(
                    amount = subValue.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = subSize.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Filled.Build,
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            ProgressBar(
                weekTarget = barSize, value = barValue, modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(start = 4.dp, end = 4.dp),
                onItemClick = {
                    isDefaultBar = !isDefaultBar
                }
            )
            ExpandedDataItem(isExpandet,isDefaultParam =false, perDeliveryValue = perDeliveryValue, perDeliveryComparable = perDeliveryComparable, perHourComparable = perHourComparable,
                perHourValue = perHourValue, perSessionValue = perSessionValue, perSessionComparable =perSessionComparable )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = itemSize.value.dp + 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            IconButton(onClick = { isExpandet = !isExpandet }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}