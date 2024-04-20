package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ShiftBuilderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.BasicWageBuilder
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.MainObjectHeaderItemData

@Composable
fun PlatformBuilderScreen() {

    val navigator = LocalNavigator.currentOrThrow


    val builderLst = listOf(
        Color.Red,Color.Yellow,Color.Green
    )
    val builderLst2 = listOf(
        "Morning","09:00","14:00","Noon","14:00","20:00","Night","20:00","02:00"
    )


    Column {
        MainObjectHeaderItem(mainObjectHeaderItemData = MainObjectHeaderItemData(),
            isPlatform = true, isBuilder = true,
            value1Color= Color.Red,
            value2Color=Color.Yellow,
            value3Color =Color.Green,
            navToPlatformBuilder = {},
            navToPlatformContext = "Platform Builder",
            navigator = navigator,
            onPlatformPick = {},
            onMainObjectClick = {},

        )

        Spacer(modifier = Modifier.height(26.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Base wage :",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )

            BasicWageBuilder()
        }



        Spacer(modifier = Modifier.height(26.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(3){
                ShiftBuilderItem(shiftOptions = listOf("12:00","12:00","12:00","12:00","12:00",),
                    modifier = Modifier.fillMaxWidth(0.85f),
                    shiftHeader = builderLst2.get(it*3),
                    startTime = builderLst2.get(it*3+1),
                    endTime = builderLst2.get(it*3+2),
                    backgroundColor = builderLst.get(it))

                Spacer(modifier = Modifier.height(26.dp),)

            }
        }



    }


}