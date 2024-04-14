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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ShiftsDivisionBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.TwoValuesProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.extraNav.ExtraNavigationIcon

@Composable
fun MainObjectHeaderItem(
    objectName: String = "March",
    onMainObjectClick:()->Unit={},
    isBuilder:Boolean = false,
    isPlatform:Boolean=false,
    pickedPlatform:String="",
    onPlatformPick:(String)->Unit={},
    mainBarValue : Float=0.205f,
    mainBarComparable : Float=0.451f,
    subValue : Float=0f,
    subComparable:Float=2f,
    value1Color : Color = Color.Blue,
    value2Color : Color = Color.Red,
    value3Color : Color = Color.Yellow,
    navToPlatformBuilder:()->Unit,
    navToPlatformContext:String = "",
    navigator: Navigator?,
    perHourValue:Float = 0f, perHourComparable:Float = 0f,
    perDeliveryValue:Float = 0f, perDeliveryComparable:Float = 0f,
    perSessionValue:Float = 0f, perSessionComparable:Float = 0f,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier
        , contentAlignment = Alignment.TopEnd) {

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
        ) {

            Text(
                text = "$objectName OverView",
                style = MaterialTheme.typography.titleLarge,
                modifier =
                Modifier.clickable { onMainObjectClick() }
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
                    PlatformArbitrator(
                        navToBuild = navToPlatformBuilder,
                        context = navToPlatformContext,
                        pickedPlatform = pickedPlatform,
                        onPlatformPick = {onPlatformPick(it)}
                    )
                else {
                    ComparedStatisticsSelector()
                }
            }
            if (isPlatform) {
                Spacer(modifier = Modifier.height(2.dp))
                ShiftsDivisionBar(
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    value1 = mainBarValue,
                    value2 = mainBarComparable,
                    value3 = subValue,
                    value1Color = value1Color,
                    value2Color = value2Color,
                    value3Color = value3Color
                )
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                TwoValuesProgressBar(
                    barVal = mainBarValue,
                    comparableVal = mainBarComparable,
                    subBarVal = subValue,
                    subComparableVal = subComparable,
                    perDeliveryValue = perDeliveryValue,
                    perDeliveryComparable = perDeliveryComparable,
                    perHourComparable = perHourComparable,
                    perHourValue = perHourValue,
                    perSessionValue = perSessionValue,
                    perSessionComparable =perSessionComparable ,
                    )

            }
        }
        if (navigator != null) {
            ExtraNavigationIcon(navigator,modifier)
        }

    }
}





@Composable
@Preview
fun thePrev() {
    //MainObjectHeaderItem(navToPlatformBuilder = {},navigator = null)

}