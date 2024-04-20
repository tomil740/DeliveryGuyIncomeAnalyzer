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
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.MainObjectHeaderItemData

@Composable
fun MainObjectHeaderItem(
    mainObjectHeaderItemData: MainObjectHeaderItemData,
    onPlatformPick:(String)->Unit,
    onMainObjectClick:(String)->Unit,
    isBuilder:Boolean = false,
    isPlatform:Boolean=false,
    value1Color : Color = Color.Blue,
    value2Color : Color = Color.Red,
    value3Color : Color = Color.Yellow,
    navToPlatformBuilder:()->Unit={},
    navToPlatformContext:String = "",
    navigator: Navigator?,
    modifier: Modifier = Modifier) {

    /*
    ToDo:
    need to define two Ui host states to host the picked graph values
    in order to preforme an wwitch on user click ...
     */


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
            Row {
                Text(
                    text =  mainObjectHeaderItemData.objectName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier =
                    Modifier.clickable { onMainObjectClick(mainObjectHeaderItemData.objectName) }//need to set some navigation function ore somthing  }
                        // .align(alignment = Alignment.CenterHorizontally)
                        .padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                PlatformArbitrator(
                    showHeader = false,
                    navToBuild = navToPlatformBuilder,
                    context = navToPlatformContext,
                    pickedPlatform = mainObjectHeaderItemData.pickedPlatform,
                    onPlatformPick = { onPlatformPick(it) }
                )
            }

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
                        pickedPlatform = mainObjectHeaderItemData.pickedPlatform,
                        onPlatformPick = {onPlatformPick(it)}
                    )
                else {
                    ComparedStatisticsSelector(onOpenArchiveMenu = {mainObjectHeaderItemData.showArchiveMenu()}, onCloseArchiveMenu = {mainObjectHeaderItemData.hideArchiveMenu()}, comparableName =mainObjectHeaderItemData.archiveComparableName )
                }
            }
            if (isPlatform) {
                Spacer(modifier = Modifier.height(2.dp))
                ShiftsDivisionBar(
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    value1 = mainObjectHeaderItemData.mainBarValue,
                    value2 = mainObjectHeaderItemData.mainBarComparable,
                    value3 = mainObjectHeaderItemData.subValue,
                    value1Color = value1Color,
                    value2Color = value2Color,
                    value3Color = value3Color
                )
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                TwoValuesProgressBar(
                    barVal = mainObjectHeaderItemData.mainBarValue,
                    comparableVal = mainObjectHeaderItemData.mainBarComparable,
                    subBarVal = mainObjectHeaderItemData.subValue,
                    subComparableVal = mainObjectHeaderItemData.subComparable,
                    perDeliveryValue = mainObjectHeaderItemData.perDeliveryValue,
                    perDeliveryComparable = mainObjectHeaderItemData.perDeliveryComparable,
                    perHourComparable = mainObjectHeaderItemData.perHourComparable,
                    perHourValue = mainObjectHeaderItemData.perHourValue,
                    //should be optional according to the object type , in order to implemnt the matched data type
                    perSessionValue =mainObjectHeaderItemData.subSumAverageIncome,
                    perSessionComparable =10005f ,
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