package com.example.deliveryguyincomeanalyzer.android.presentation.screens.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ArchiveItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.PlatformArbitrator
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ShiftItem
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType


@Composable
fun ArchiveMenu(valueObjectType:SumObjectsType,menuObj:SumObjectInterface,workingPlatformRemoteMenu:List<WorkingPlatformOptionMenuItem>,
                workingPlatformCustomMenu:List<WorkingPlatformOptionMenuItem>,
                onObjectPick:(SumObjectInterface?)->Unit,onWorkingPlatformPick:(String)->Unit,
                comparableObj : SumObjectInterface,
                modifier:Modifier) {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        if (valueObjectType
                            == menuObj.subObjects?.get(0)?.objectType
                        ) {
                            Color.Green
                        } else {
                            Color.White
                        }
                    )
                    .fillMaxSize(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {

                    IconButton(modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(56.dp), onClick = { onObjectPick(null) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Text(
                            text = "Required type $valueObjectType : ",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                //.align(alignment = Alignment.Start)
                                .padding(start = 12.dp, end = 40.dp),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        PlatformArbitrator(
                            textColor = MaterialTheme.colorScheme.primary,
                            navToBuild = {},
                            pickedPlatform = menuObj.platform,
                            onPlatformPick = { onWorkingPlatformPick(it) },
                            theLst = workingPlatformRemoteMenu.plus(
                                workingPlatformCustomMenu
                            )
                        )
                        if (valueObjectType == SumObjectsType.AllTimeSum) {

                            Spacer(modifier = Modifier.height(23.dp))

                            Button(onClick = { onObjectPick(menuObj)},
                                colors = ButtonDefaults.buttonColors().copy(contentColor = MaterialTheme.colorScheme.secondary)) {
                                Text(
                                    text = "AllTime Sum",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(start = 12.dp, end = 12.dp),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (menuObj.subObjects != null) {
                        items(menuObj.subObjects!!) { theObj ->
                            ArchiveItem(
                                objectName = theObj.objectName,
                                barSizeParam = theObj.totalIncome * 1.3f,
                                barValueParam = theObj.totalIncome,
                                subSizeParam = theObj.totalTime * 1.3f,
                                subValueParam = theObj.totalTime,
                                perHourVal = theObj.averageIncomePerHour,
                                perHourComparable = comparableObj.averageIncomePerHour,
                                perDeliveryVal = theObj.averageIncomePerDelivery,
                                perDeliveryComparable = comparableObj.averageIncomePerDelivery,
                                perSessionVal = theObj.averageIncomeSubObj,
                                perSessionComparable = comparableObj.averageIncomeSubObj,
                                onHeaderClick = {
                                   onObjectPick(theObj)
                                },
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )
                        }
                    } else {
                        if (menuObj.shiftsSumByType != null) {

                            items(menuObj.shiftsSumByType!!) {
                                Spacer(modifier = Modifier.height(20.dp))
                                LazyRow {
                                    item {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        ShiftItem(shiftSum = it.shiftSum,
                                            morningSum = it.totalShifts,
                                            onHeaderClick = {
                                                onObjectPick(
                                                    it.shiftSum
                                                )

                                            })
                                        Spacer(modifier = Modifier.width(6.dp))

                                    }
                                    items(it.shiftSums) { yy ->
                                        Spacer(modifier = Modifier.width(6.dp))
                                        ShiftItem(shiftSum = yy,
                                            onHeaderClick = {
                                                onObjectPick(
                                                    it.shiftSum
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