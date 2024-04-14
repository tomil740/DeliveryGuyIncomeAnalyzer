package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ArchiveItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.BooleanItemsSwitch
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.GraphItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.domain.model.GraphState

@Composable
fun ObjectItemScreen(objectName:String,modifier: Modifier = Modifier) {

    val navigator = LocalNavigator.currentOrThrow

    //to use the floating expandet item arrow mark , we must implement it in box layout

    var isItem1 by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .offset(y = -20.dp)
                    .size(58.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }) { paddingVal ->

        Column(
            modifier.heightIn(max = 1000.dp).verticalScroll(rememberScrollState())
                .padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MainObjectHeaderItem(objectName=objectName, navToPlatformBuilder = {}, navigator = navigator,modifier = modifier)

            Spacer(modifier = Modifier.height(18.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                BooleanItemsSwitch(
                    item1 = "Statistics",
                    onItem1 = { isItem1 = true },
                    item2 = "Archive",
                    onItem2 = { isItem1 = false },
                    isItem1
                )
            }

            Column(
                modifier.heightIn(max = 1000.dp).padding(paddingVal),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

               AnimatedVisibility(visible = isItem1) {
                        Column(
                            modifier.heightIn(max = 1000.dp).verticalScroll(rememberScrollState())
                                .padding(paddingVal),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GraphItem(
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                                perHourValueGraphState = GraphState(ogLst = listOf(75f,94f,59f,53f,27f,19f,12f))
                                ,perHourComparableGraphState =GraphState(ogLst = listOf(130f,20f,90f,90f,20f,30f,10f)),
                                perDeliverValueGraphState = GraphState(ogLst = listOf(42f,33f,26f,35f,45f,66f,78f)),
                                perDeliverComparableGraphState=GraphState(ogLst = listOf(49f,43f,46f,45f,45f,66f,88f))
                            )
                        }
                    }
                AnimatedVisibility(visible = !isItem1) {
                    LazyColumn(
                        modifier.padding(paddingVal),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        items(20) {
                            ArchiveItem(
                                objectName = "January",
                                barSizeParam = 8000f,
                                barValueParam = 6655f,
                                subSizeParam = 215f,
                                subValueParam = 144.8f,
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}