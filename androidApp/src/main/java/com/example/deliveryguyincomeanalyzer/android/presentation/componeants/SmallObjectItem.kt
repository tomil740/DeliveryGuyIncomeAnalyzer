package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.SmallObjectItemBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import java.time.DayOfWeek

@Composable
fun SmallObjectItem(objectHeader: String, hoursComparable: Float, hoursValue : Float, income: Float,navToObject:(String)->Unit) {
    val modifier = Modifier.clickable { navToObject(objectHeader) }
        .fillMaxWidth()
        .padding(start = 19.dp, end = 19.dp)

    Column(modifier = modifier, verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        UnitDisplay(amount = hoursComparable, unitIcon = Icons.Default.Notifications, amountColor=MaterialTheme.colorScheme.onSecondaryContainer,
            unitColor =MaterialTheme.colorScheme.onSecondaryContainer,isMainObj=false,amountTextStyle=MaterialTheme.typography.titleLarge, modifier = Modifier.offset(y=-21.dp))

        SmallObjectItemBar(barSize = 8.5f, barValue = 6f,modifier = Modifier
            .size(width = 80.dp, height = 54.dp)
            .offset(y = -5.dp,x= 5.dp)
           )


        Text(text = objectHeader, style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)

        Box(modifier = Modifier.border(5.dp, MaterialTheme.colorScheme.onSecondaryContainer)) {

            Column(Modifier.background(MaterialTheme.colorScheme.secondaryContainer).padding(9.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                UnitDisplay(amount = hoursValue, unitIcon = Icons.Default.Star,amountColor=MaterialTheme.colorScheme.onSecondaryContainer, unitColor =MaterialTheme.colorScheme.onSecondaryContainer,amountTextStyle=MaterialTheme.typography.titleLarge)
                UnitDisplay(amount = income, unitIcon = Icons.Default.Notifications,amountColor=MaterialTheme.colorScheme.onSecondaryContainer, unitColor =MaterialTheme.colorScheme.onSecondaryContainer,isMainObj=false,amountTextStyle=MaterialTheme.typography.titleLarge)


            }

        }

    }
}

