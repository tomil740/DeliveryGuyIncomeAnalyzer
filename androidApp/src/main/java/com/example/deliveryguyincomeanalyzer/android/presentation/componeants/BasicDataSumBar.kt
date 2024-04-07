package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import java.math.RoundingMode
@Composable
fun BasicDataSumBar(barVal:Float, comparableVal:Float, barColor: Color, valueColor: Color, attributeHeader:String,
                         modifier: Modifier = Modifier) {


    var barSize by remember { mutableStateOf(comparableVal) }
    var barValue by remember { mutableStateOf(barVal) }


    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.Start
        ) {

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                Text(
                    text = attributeHeader,
                    style = MaterialTheme.typography.titleLarge,
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            Spacer(modifier = Modifier.height(3.dp))
                ProgressBar(
                    weekTarget = comparableVal, value = barVal, modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(start = 4.dp, end = 4.dp),
                    onItemClick = {},
                    barColor = barColor, valueColor = valueColor
                )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //as for now I pass this behaviour to the unit display , at future probably will nav to the specific value analytics
                UnitDisplay(
                    amount = barVal.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.primary,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = true,
                )

                UnitDisplay(
                    amount = comparableVal.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                    unitIcon = Icons.Default.Notifications,
                    amountColor = MaterialTheme.colorScheme.primary,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.primary,
                    isMainObj = true,
                )
            }
        }
    }
}
