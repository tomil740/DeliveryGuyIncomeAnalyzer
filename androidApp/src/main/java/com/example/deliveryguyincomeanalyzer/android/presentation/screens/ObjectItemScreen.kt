package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ArchiveItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.RadioItemsSwitch
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.GraphItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ShiftItem
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.ObjectItemScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.util.ArchiveMenu
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.GraphState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectIntType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getGeneralSum
import com.example.deliveryguyincomeanalyzer.domain.model.util.uiSubModelMapers.sumObjectToMainObjectHeaderItemData
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.ObjectItemStatesAndEvents
import kotlinx.coroutines.flow.consumeAsFlow

@OptIn(InternalVoyagerApi::class)
@Composable
fun ObjectItemScreen(initializeObj: SumObjectInterface? =null,
                     objectItemStatesAndEvents: ObjectItemStatesAndEvents, modifier: Modifier = Modifier) {

    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow

    var shouldInit by remember { mutableStateOf(true)}

    if (initializeObj != null && shouldInit) {
        objectItemStatesAndEvents.initializeAnObject(initializeObj)
    }
    if(objectItemStatesAndEvents.uiState.objectValueSum.objectType == SumObjectsType.AllTimeSum)
        shouldInit = true

    //to use the floating expandet item arrow mark , we must implement it in box layout

    var statisticsOrArchive by remember { mutableIntStateOf(2) }
    var sumObjType by remember { mutableIntStateOf(1) }

    LaunchedEffect(sumObjType) {
        val theType = getSumObjectType(sumObjType)
        when(objectItemStatesAndEvents.uiState.objectValueSum.sumObjectSourceType){
            SumObjectSourceType.Archive->{}//nothing that should not be presented to the user at all}
            SumObjectSourceType.GeneralStatistics->{
                objectItemStatesAndEvents.onValueGeneralStatisticsPick(theType)
                }
            SumObjectSourceType.MyStatistics->{objectItemStatesAndEvents.onValueUserStatPick(theType)}
        }
    }


    Box(modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .offset(y = -20.dp)
                        .size(58.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Filled.Add, "Small floating action button.")
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
            ) { paddingVal ->

            Column(
                modifier
                    .heightIn(max = 1000.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LaunchedEffect(objectItemStatesAndEvents.uiState.uiMessage) {
                    objectItemStatesAndEvents.uiState.uiMessage.consumeAsFlow().collect{
                        snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Long)
                    }
                }
                MainObjectHeaderItem(
                    //the arcive comparable pick will be on the spesifc matched function
                    mainObjectHeaderItemData = sumObjectToMainObjectHeaderItemData(
                        value = objectItemStatesAndEvents.uiState.objectValueSum,
                        comparable = objectItemStatesAndEvents.uiState.objectComparableSum,
                        showArchiveMenu = { objectItemStatesAndEvents.onOpenMenu() },
                        hideArchiveMenu = { objectItemStatesAndEvents.onCloseMenu() },
                        platformOptionMenu1 = objectItemStatesAndEvents.uiState.workingPlatformRemoteMenu,
                        platformOptionMenu2 = objectItemStatesAndEvents.uiState.workingPlatformCustomMenu
                    ),
                    navigator = navigator,
                    navToPlatformContext = "",
                    onMainObjectClick = { },
                    onValueArchivePick = {
                        //without applying new navigation we will stay on the old initalize function again...
                        navigator.push(ObjectItemScreenClass())
                        objectItemStatesAndEvents.onValueArchiveTopMenu(it) },
                    onComparableGeneralStatPick = {objectItemStatesAndEvents.onComparableGeneralStatisticsPick(it)},
                    onComparableUserStatPick = {objectItemStatesAndEvents.onComparableUserStatPick(it)},
                    onValueUserStatPick = {
                        navigator.push(ObjectItemScreenClass())
                        objectItemStatesAndEvents.onValueUserStatPick(it)},
                    onValueGeneralStatPick = {
                        navigator.push(ObjectItemScreenClass())
                        objectItemStatesAndEvents.onValueGeneralStatisticsPick(it)},

                    navToPlatformBuilder = {},
                    modifier = modifier
                )

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        if(objectItemStatesAndEvents.uiState.objectValueSum.sumObjectSourceType != SumObjectSourceType.Archive){
                            statisticsOrArchive = 1
                            RadioItemsSwitch(
                                item1 = "Month",
                                onItem1 = { sumObjType=1 },
                                item2 = "Work Session",
                                item3 = "Shift",
                                onItem2 = { sumObjType = 2 },
                                onItem3 = {sumObjType = it},
                                pickedItem = getSumObjectIntType(objectItemStatesAndEvents.uiState.objectValueSum.objectType.name),
                                shiftType = objectItemStatesAndEvents.uiState.objectValueSum.shiftType ?: ""
                            )
                        }else {
                            RadioItemsSwitch(
                                item1 = "Statistics",
                                onItem1 = { statisticsOrArchive = 1 },
                                item2 = "Archive",
                                onItem2 = { statisticsOrArchive = 2 },
                                pickedItem = statisticsOrArchive,
                                shiftType = objectItemStatesAndEvents.uiState.objectValueSum.shiftType ?: ""
                            )
                        }
                    }

                    Column(
                        modifier
                            .heightIn(max = 1000.dp)
                            .padding(paddingVal),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AnimatedVisibility(visible = statisticsOrArchive==1) {
                            Column(
                                modifier
                                    .heightIn(max = 1000.dp)
                                    .verticalScroll(rememberScrollState())
                                    .padding(paddingVal),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                GraphItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    perHourValueGraphState = objectItemStatesAndEvents.uiState.objectValueSum.incomePerSpecificHour
                                        ?: GraphState(
                                            ogLst = listOf(
                                                130f,
                                                20f,
                                                90f,
                                                90f,
                                                20f,
                                                30f,
                                                10f
                                            )
                                        ),
                                    perHourComparableGraphState = objectItemStatesAndEvents.uiState.objectComparableSum.incomePerSpecificHour
                                        ?: GraphState(
                                            ogLst = listOf(
                                                130f,
                                                20f,
                                                90f,
                                                90f,
                                                20f,
                                                30f,
                                                10f
                                            )
                                        ),
                                    perDeliverValueGraphState = objectItemStatesAndEvents.uiState.objectValueSum.workPerHour
                                        ?: GraphState(
                                            ogLst = listOf(
                                                42f,
                                                33f,
                                                26f,
                                                35f,
                                                45f,
                                                66f,
                                                78f
                                            )
                                        ),
                                    perDeliverComparableGraphState = objectItemStatesAndEvents.uiState.objectComparableSum.workPerHour
                                        ?: GraphState(
                                            ogLst = listOf(
                                                49f,
                                                43f,
                                                46f,
                                                45f,
                                                45f,
                                                66f,
                                                88f
                                            )
                                        )
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(top = 32.dp, start = 14.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Shifts statistics : ",
                                        style = MaterialTheme.typography.displaySmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )

                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                val b =
                                   objectItemStatesAndEvents.uiState.objectValueSum.shiftsSumByType

                                if (!b.isNullOrEmpty()) {
                                    val a = getGeneralSum(b)
                                    LazyRow {
                                        item {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            ShiftItem(
                                                shiftSum = a.shiftSum,
                                                morningSum = a.totalMorning,
                                                noonSum = a.totalNoon,
                                                nightSum = a.totalNight
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))

                                        }
                                        items(objectItemStatesAndEvents.uiState.objectValueSum.shiftsSumByType!!) { yy ->
                                            Spacer(modifier = Modifier.width(6.dp))
                                            ShiftItem(shiftSum = yy.shiftSum)
                                            Spacer(modifier = Modifier.width(6.dp))

                                        }
                                    }
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                        AnimatedVisibility(visible = statisticsOrArchive==2) {
                            LazyColumn(
                                modifier.padding(paddingVal),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (objectItemStatesAndEvents.uiState.objectValueSum.subObjects != null) {
                                    items(objectItemStatesAndEvents.uiState.objectValueSum.subObjects!!) { theObj ->
                                        val comparable = objectItemStatesAndEvents.uiState.objectComparableSum.subObjects?.get(0) ?:
                                        objectItemStatesAndEvents.uiState.objectComparableSum
                                        if (theObj.objectType == SumObjectsType.ShiftSession) {

                                            Spacer(modifier = Modifier.height(24.dp))

                                            ShiftItem(
                                                shiftSum = theObj
                                            )

                                            Spacer(modifier = Modifier.height(24.dp))

                                        } else {
                                            ArchiveItem(
                                                objectName = theObj.objectName,
                                                barSizeParam = theObj.totalIncome * 1.3f,
                                                barValueParam = theObj.totalIncome,
                                                subSizeParam = theObj.totalTime * 1.3f,
                                                subValueParam = theObj.totalTime,
                                                perHourVal = theObj.averageIncomePerHour,
                                                perHourComparable = comparable.averageIncomePerHour,
                                                perDeliveryVal = theObj.averageIncomePerDelivery,
                                                perDeliveryComparable = comparable.averageIncomePerDelivery,
                                                perSessionVal = theObj.averageIncomeSubObj,
                                                perSessionComparable = comparable.averageIncomeSubObj,
                                                onHeaderClick = {
                                                    navigator.push(
                                                        ObjectItemScreenClass(
                                                            initializeObj = theObj
                                                        )
                                                    )
                                                },
                                                modifier = Modifier.fillMaxWidth(0.9f)
                                            )
                                        }
                                    }
                                }
                                if (objectItemStatesAndEvents.uiState.objectValueSum.shiftsSumByType != null) {

                                    item {
                                        Spacer(modifier = Modifier.height(24.dp))

                                        Text(
                                            text = "ShiftsSum : ",
                                            style = MaterialTheme.typography.titleLarge,
                                            modifier = Modifier
                                                //.align(alignment = Alignment.Start)
                                                .padding(start = 12.dp, end = 40.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))

                                    }

                                    items(
                                        objectItemStatesAndEvents.uiState.objectValueSum.shiftsSumByType
                                            ?: listOf()
                                    ) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                        LazyRow {

                                            item {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                ShiftItem(
                                                    shiftSum = it.shiftSum,
                                                    morningSum = it.totalShifts
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))

                                            }


                                            items(it.shiftSums) { yy ->
                                                Spacer(modifier = Modifier.width(6.dp))
                                                ShiftItem(shiftSum = yy, isStatisticsObj = true,
                                                    onHeaderClick = {
                                                        navigator.push(
                                                            ObjectItemScreenClass(initializeObj = yy)
                                                        )
                                                    })
                                                Spacer(modifier = Modifier.width(6.dp))

                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }
            }
        }
    }



    Column(modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        AnimatedVisibility(visible = objectItemStatesAndEvents.uiState.showComparableMenu) {
            ArchiveMenu(
                valueObjectType = objectItemStatesAndEvents.uiState.objectValueSum.objectType,
                menuObj = objectItemStatesAndEvents.uiState.comparableDataMenu,
                workingPlatformRemoteMenu = objectItemStatesAndEvents.uiState.workingPlatformRemoteMenu,
                workingPlatformCustomMenu = objectItemStatesAndEvents.uiState.workingPlatformCustomMenu,
                onObjectPick = {objectItemStatesAndEvents.onArchiveComparableMenuPick(it)},
                onWorkingPlatformPick = {objectItemStatesAndEvents.onComparablePlatform(it)},
                comparableObj = objectItemStatesAndEvents.uiState.objectComparableSum,
                modifier = modifier
            )
        }
    }


}