package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.DeliveryLiveItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ExtrasTextField
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.VerticalBarProgressItem
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.OverViewScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.PlatformBuilderScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.util.ArchiveMenu
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.uiSubModelMapers.sumObjectToMainObjectHeaderItemData
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderStatesAndEvents
import kotlinx.coroutines.flow.consumeAsFlow

/*
DeclareLiveBuilderScreen :
This screen implemented a bit different from the classic layout because of questionable use, the rotate on the vertical bar
because of that use we kind of get the original start point and the rest of the object tangle in all of the attribute on it
while in practice it will be different
the Solution is to adjust the new rotate start point of the object with the offSet attributes, and that can be implement by taking the wanted object measures
and set the start point offSet to around half of the values (when rotate 90 degrees)
the result will be a little bit wired UI object that works in practice but will stay steel to layout changes (beside dp changes)

*as a note for mySelf need to be solved batter in the future *

 */


@Composable
fun DeclareLiveBuilderScreen(modifier: Modifier=Modifier,declareBuilderStatesAndEvents: DeclareBuilderStatesAndEvents) {

    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.current

    var showActionOptions by remember { mutableStateOf(false) }


    LaunchedEffect(declareBuilderStatesAndEvents.uiState.liveBuilderState.delivers) {
        declareBuilderStatesAndEvents.saveLiveBuilderState(declareBuilderStatesAndEvents.uiState.liveBuilderState)
    }

    //to use the floating expandet item arrow mark , we must implement it in box layout
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            AnimatedVisibility (showActionOptions) {
                Column(modifier = Modifier.offset(x = -35.dp, y = -20.dp)) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            declareBuilderStatesAndEvents.onSubmitDeclare()
                            declareBuilderStatesAndEvents.onDeleteDeclare
                            navigator?.push(OverViewScreenClass())
                            showActionOptions=!showActionOptions},
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Icon(Icons.Filled.Check, "Small floating action button.", modifier = Modifier.size(32.dp))

                        Text(text = " Submit")

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ExtendedFloatingActionButton(
                        onClick = {declareBuilderStatesAndEvents.deleteLiveBuilderState()
                            declareBuilderStatesAndEvents.getLiveBuilderState()
                                  navigator?.push(OverViewScreenClass())},
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Icon(Icons.Filled.Clear, "Small floating action button.", modifier = Modifier.size(32.dp))

                        Text(text = " Delete")

                    }
                }
            }
            AnimatedVisibility(!showActionOptions){
                SmallFloatingActionButton(
                    onClick = { showActionOptions=!showActionOptions},
                    modifier = Modifier
                        .offset(x = -45.dp, y = -20.dp)
                        .size(58.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Filled.Edit, "Small floating action button.")
                }
            }
        }
    ) { paddingVal->

        Box(modifier = Modifier.padding(paddingVal),  contentAlignment = Alignment.TopEnd) {

            Column(modifier.padding(paddingVal)) {

                LaunchedEffect(declareBuilderStatesAndEvents.uiState.uiMessage) {
                    declareBuilderStatesAndEvents.uiState.uiMessage.consumeAsFlow().collect{
                        snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Long)
                    }
                }

                MainObjectHeaderItem(
                    isBuilderWorkSession = true,
                    navToPlatformBuilder = {
                        if (navigator != null) {
                            navigator.push(PlatformBuilderScreenClass())
                        }
                    },
                    onPlatformPick = {declareBuilderStatesAndEvents.onPickWorkingPlatform(it)},
                    navigator = navigator,
                   mainObjectHeaderItemData = sumObjectToMainObjectHeaderItemData(value = declareBuilderStatesAndEvents.uiState.currentSum,
                       comparable = declareBuilderStatesAndEvents.uiState.comparableObj,
                       platformOptionMenu1 = declareBuilderStatesAndEvents.uiState.workingPlatformRemoteMenu,
                       platformOptionMenu2 = declareBuilderStatesAndEvents.uiState.workingPlatformCustomMenu,
                       showArchiveMenu = { declareBuilderStatesAndEvents.onOpenComparableArchiveMenu() },
                       hideArchiveMenu = { declareBuilderStatesAndEvents.onCloseComparableArchiveMenu() },
                   ),
                    onMainObjectClick = {},
                    onGeneralStatPick = {declareBuilderStatesAndEvents.onComparablePlatform(it)},
                    onMyStatPick = {declareBuilderStatesAndEvents.onComparableStatPick(it)},
                    modifier = modifier)

                ExtrasTextField(commonExras = listOf("1", "5", "10", "15", "20"), onSend = declareBuilderStatesAndEvents.onAddDeliveryItem)

                Spacer(modifier = Modifier.height(44.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(declareBuilderStatesAndEvents.uiState.liveBuilderState.deliversItem.size) {
                            val item  = declareBuilderStatesAndEvents.uiState.liveBuilderState.deliversItem.get(declareBuilderStatesAndEvents.uiState.liveBuilderState.deliversItem.size - 1-it)
                            DeliveryLiveItem(
                                theIndex = declareBuilderStatesAndEvents.uiState.liveBuilderState.deliversItem.size - 1-it,
                                theValue = item.extra,
                                onDelete = { declareBuilderStatesAndEvents.onDeleteDeliveryItem(it) },
                                modifier = Modifier.size(height = 56.dp, width = 210.dp)
                            )
                            Spacer(modifier = Modifier.height(21.dp))

                        }
                    }

                    VerticalBarProgressItem(
                        barValue = declareBuilderStatesAndEvents.uiState.currentSum.delivers.toFloat(),
                        comparableValue = declareBuilderStatesAndEvents.uiState.comparableObj.delivers.toFloat(),
                        attributeName = "Delivers",
                        modifier = Modifier
                            .size(height = 60.dp, width = 380.dp)
                            .offset(x = -148.dp, y = -30.dp)
                    )

                    VerticalBarProgressItem(
                        barValue = declareBuilderStatesAndEvents.uiState.liveBuilderState.extras,
                        comparableValue = declareBuilderStatesAndEvents.uiState.comparableObj.extraIncome,
                        attributeName = "extra",
                        modifier = Modifier
                            .size(height = 60.dp, width = 380.dp)
                            .offset(x = 158.dp, y = -30.dp)
                    )

                }


            }
        }
    }

    Column(modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        AnimatedVisibility(visible = declareBuilderStatesAndEvents.uiState.showComparableMenu) {
            ArchiveMenu(
                valueObjectType = SumObjectsType.WorkSession,
                menuObj = declareBuilderStatesAndEvents.uiState.comparableMenuData,
                workingPlatformRemoteMenu = declareBuilderStatesAndEvents.uiState.workingPlatformRemoteMenu,
                workingPlatformCustomMenu = declareBuilderStatesAndEvents.uiState.workingPlatformCustomMenu,
                onObjectPick = {declareBuilderStatesAndEvents.onArchiveComparableMenuPick(it)},
                onWorkingPlatformPick = {declareBuilderStatesAndEvents.onComparablePlatform(it)},
                comparableObj = declareBuilderStatesAndEvents.uiState.comparableObj,
                modifier = modifier
            )
        }
    }
}