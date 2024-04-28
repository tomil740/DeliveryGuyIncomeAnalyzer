package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.TwoValuesProgressBar
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftItem(shiftSum: SumObjectInterface = ShiftDomain("Morrning", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 3, hour = 3, minute = 3), endTime = LocalDateTime(year = 2024, month = Month.MAY, dayOfMonth = 3, hour = 3, minute = 3),
    time = 5f, baseIncome = 5f, extraIncome = 4f, delivers = 5, shiftType = "Morrning", dataPerHour = listOf()
).toShiftSum(),isStatisticsObj:Boolean=false,onHeaderClick : () ->Unit={},morningSum:Int=-1,noonSum:Int=-1,nightSum:Int=-1) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .requiredWidth(300.dp)
        .background(MaterialTheme.colorScheme.primary), horizontalAlignment = Alignment.CenterHorizontally) {

        if(morningSum!=-1&&noonSum!=-1&&nightSum!=-1){
            Row(modifier = Modifier.clickable { onHeaderClick() }
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column( horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Morning",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = morningSum.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Noon",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = noonSum.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Night",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = nightSum.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }
            }
        }else{
            var theVal=morningSum
            if(noonSum!=-1)  {
                theVal = noonSum
            }
            else if(nightSum!=-1)
                theVal = nightSum

            if(theVal!=-1){

        Row(modifier = Modifier.clickable { onHeaderClick() }.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Average ${shiftSum.shiftType} :",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "total of : ${theVal}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

        }else {
                Row(modifier = Modifier.clickable { onHeaderClick() }.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = if (isStatisticsObj){"${shiftSum.objectName} :"}else{"${shiftSum.shiftType} :"},
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = shiftSum?.startTime?.time.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(start = 20.dp, end = 20.dp)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward, contentDescription = null,
                    Modifier
                        .size(36.dp)
                        .offset(y = -6.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )

                Text(
                    text = shiftSum?.endTime?.time.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(start = 20.dp, end = 20.dp)
                )

            }
        }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TwoValuesProgressBar(barVal = (shiftSum.baseIncome+ shiftSum.extraIncome),
            comparableVal = (shiftSum.baseIncome+ shiftSum.extraIncome)*1.3f, subBarVal = shiftSum.totalTime,
            subComparableVal = shiftSum.totalTime*1.3f, isFullBar = true, perDeliveryValue = shiftSum.averageIncomePerDelivery, perDeliveryComparable = shiftSum.averageIncomePerDelivery*1.3f,
            perHourComparable = shiftSum.averageIncomePerHour*1.3f, perHourValue = shiftSum.averageIncomePerHour
            )

    }


}