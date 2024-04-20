package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import java.math.RoundingMode

@Composable
fun ArchiveItem(objectName:String,barSizeParam:Float,barValueParam:Float
                ,subSizeParam:Float, subValueParam:Float,onHeaderClick : () ->Unit
                ,modifier: Modifier=Modifier) {

    var isDefaultBar by remember { mutableStateOf(true) }
    var isExpandet by remember { mutableStateOf(false) }
    var barSize by remember { mutableStateOf(barSizeParam) }
    var barValue by remember { mutableStateOf(barValueParam) }
    var subSize by remember { mutableStateOf(subSizeParam) }
    var subValue by remember { mutableStateOf(subValueParam) }

    LaunchedEffect(key1 = objectName) {
         isDefaultBar =true
         isExpandet =false
         barSize =barSizeParam
         barValue =barValueParam
         subSize=subSizeParam
         subValue=subValueParam

    }

    var itemSize by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current


    LaunchedEffect(key1 = isDefaultBar) {
        val a = barSize
        val b = barValue
        barSize = subSize
        barValue = subValue
        subValue = b
        subSize = a
    }

    Box(modifier = modifier
        .fillMaxWidth(), contentAlignment = Alignment.Center) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .onGloballyPositioned {
                    itemSize = with(density) {
                        it.size.height.toDp()
                    }
                },
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                Modifier
                    .clickable { onHeaderClick() }
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = objectName + " :",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

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
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = barSize.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.Notifications,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
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
                    amount = subSize.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = subValue.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Filled.Build,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            ProgressBar(
                weekTarget = barSize,
                value = barValue,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally),
                onItemClick = {
                    isDefaultBar = !isDefaultBar
                },
                barColor = MaterialTheme.colorScheme.onSecondary,
                valueColor = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExpandedDataItem(isExpandet,barColor = MaterialTheme.colorScheme.onSecondary,
                valueColor = MaterialTheme.colorScheme.secondary,textColor=MaterialTheme.colorScheme.onSecondaryContainer)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height = itemSize.value.dp + 38.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            IconButton(onClick = { isExpandet = !isExpandet }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}