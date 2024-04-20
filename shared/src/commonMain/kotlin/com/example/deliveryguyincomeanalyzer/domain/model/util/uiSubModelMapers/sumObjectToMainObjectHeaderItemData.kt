package com.example.deliveryguyincomeanalyzer.domain.model.util.uiSubModelMapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.getSumObjectHeader
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.MainObjectHeaderItemData

fun sumObjectToMainObjectHeaderItemData(value: SumObjectInterface, comparable:SumObjectInterface, hideArchiveMenu:()->Unit={}, showArchiveMenu:()->Unit={}):MainObjectHeaderItemData{
    return MainObjectHeaderItemData(
        objectName = getSumObjectHeader(value.objectType, shiftType = value.shiftType, startTime = value.startTime),
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
        subSumAverageIncome = value.averageIncomeSubObj

    )
}