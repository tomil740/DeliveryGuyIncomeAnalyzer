package com.example.deliveryguyincomeanalyzer.android.presentation.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
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
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.PlatformArbitrator
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ShiftItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.SmallObjectItem
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.DeclareBuilderScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.ObjectItemScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.PlatformBuilderScreenClass
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.MainObjectHeaderItemData

@Composable
fun OverViewScreen(modifier:Modifier=Modifier) {

    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { navigator.push(DeclareBuilderScreenClass()) },
                modifier = Modifier
                    .offset(y = -20.dp)
                    .size(58.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }) { paddingValues ->

        Box(
            modifier = modifier.padding(paddingValues)
                .fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    MainObjectHeaderItem(onMainObjectClick = { navigator.push(ObjectItemScreenClass()) },
                        navigator = navigator, navToPlatformBuilder = {}, onPlatformPick = {}, mainObjectHeaderItemData = MainObjectHeaderItemData()
                    )

                    Text(
                        text = "Recent work :",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(26.dp))
                }

                item {

                    LazyRow {
                        items(7) {
                            Box(Modifier.padding(start = 4.dp, end = 4.dp)) {
                                SmallObjectItem(
                                    objectHeader = "Sunday",
                                    hoursComparable = 8f,
                                    hoursValue = 7f,
                                    income = 750f,
                                    navToObject = {navigator.push(ObjectItemScreenClass())}
                                )
                            }
                        }
                    }
                }

                item {

                    Spacer(modifier = Modifier.height(22.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PlatformArbitrator(textColor = MaterialTheme.colorScheme.primary, navToBuild = {navigator.push(PlatformBuilderScreenClass())})
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(4) {
                            Spacer(modifier = Modifier.width(6.dp))
                            ShiftItem()
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
        }
    }
}
