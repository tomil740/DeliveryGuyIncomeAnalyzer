package com.example.deliveryguyincomeanalyzer.domain.model.util.uiSubModelMapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.getSumObjectHeader
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.MainObjectHeaderItemData
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem

fun sumObjectToMainObjectHeaderItemData(value: SumObjectInterface, comparable:SumObjectInterface, hideArchiveMenu:()->Unit={}, showArchiveMenu:()->Unit={}
                                        ,platformOptionMenu1: List<WorkingPlatformOptionMenuItem> = listOf(),platformOptionMenu2: List<WorkingPlatformOptionMenuItem> = listOf() ):MainObjectHeaderItemData{


    return MainObjectHeaderItemData(
        objectName = getSumObjectHeader(value.objectType, shiftType = value.shiftType, startTime = value.startTime),
        pickedPlatformComparable = comparable.platform,
        pickedPlatform= value.platform,
        archiveComparableName = "${comparable.objectName},${comparable.platform}",
        showArchiveMenu = {showArchiveMenu()},
        hideArchiveMenu={hideArchiveMenu()},
        mainBarValue=value.totalIncome ,
        mainBarComparable= comparable.totalIncome,
        subValue=value.totalTime,
        subComparable=comparable.totalTime,
        perDeliveryValue=value.averageIncomePerDelivery,
        perDeliveryComparable=comparable.averageIncomePerDelivery,
        perHourValue=value.averageIncomePerHour,
        perHourComparable=comparable.averageIncomePerHour,
        subSumAverageIncome = value.averageIncomeSubObj,
        subSumComparableAverageIncome = comparable.averageIncomeSubObj,
        platformsMenu1 = platformOptionMenu1,
        platformsMenu2 = platformOptionMenu2,
        ComparableObjectSourceType = comparable.sumObjectSourceType



    )
}