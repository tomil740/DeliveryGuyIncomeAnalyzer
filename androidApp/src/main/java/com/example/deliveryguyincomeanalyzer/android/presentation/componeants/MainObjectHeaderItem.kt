package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.R
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.CircleProgressItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import java.math.RoundingMode

@Composable
fun MainObjectHeaderItem(
    objectName: String = "ObjectOverView",
    isBuilder:Boolean = false,
    modifier: Modifier = Modifier) {

    //we will get the object attributes to present (hours and income with there targets)
    var averageincome = 63112f
    var income= 2400f
    var averageWorkTime = 180.324f
    var workTime = 144.79f
    //

    var isExpandet by remember { mutableStateOf(false) }


    var isDefaultBar by remember { mutableStateOf(true) }
    var barSize by remember { mutableStateOf(averageincome) }
    var barValue by remember { mutableStateOf(income) }
    var subSize by remember { mutableStateOf(averageWorkTime) }
    var subValue by remember { mutableStateOf(workTime) }

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

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .onGloballyPositioned {
                    itemSize = with(density) {
                        it.size.height.toDp()
                    }
                },
            verticalArrangement = Arrangement.Top
        ) {


            Text(
                text = objectName,
                style = MaterialTheme.typography.titleLarge,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(start = 12.dp, end = 40.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .offset(x = -2.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            )
            {
                if (isBuilder)
                    PlatformArbitrator()
                else {
                    ComparedObjectSelector()
                }
            }
            Column(modifier = Modifier.offset(y = -16.dp)) {
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
                        amount = subSize.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                        unitIcon = Icons.Default.AccountBox,
                        amountColor = MaterialTheme.colorScheme.onPrimary,
                        amountTextStyle = MaterialTheme.typography.titleMedium,
                        unitColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Bottom),
                        isMainObj = false,
                        onItemClick = { isDefaultBar = !isDefaultBar }

                    )

                    UnitDisplay(
                        amount = subValue.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
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
                        .height(30.dp),
                    onItemClick = {
                        isDefaultBar = !isDefaultBar
                    }
                )

                Spacer(modifier = Modifier.height(3.dp))

            }

            ExpandedDataItem(isExpandet,isDefaultParam =false)


        }
        Box(
            modifier = Modifier.fillMaxWidth().height(height = itemSize.value.dp + 16.dp),
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




@Composable
@Preview
fun thePrev() {
    MainObjectHeaderItem()

}