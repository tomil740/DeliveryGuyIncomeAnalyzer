package com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels

import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType

//when it will be compose multiplatform this kind of data will be in the shared module
data class MainObjectHeaderItemData(
    val objectName : String="",
    val objectPlatform:String="",
    val pickedPlatformComparable : String="",
    val archiveComparableName:String="",
    val valueObjectSourceType: SumObjectSourceType = SumObjectSourceType.Archive,
    val ComparableObjectSourceType: SumObjectSourceType = SumObjectSourceType.Archive,
    val platformsMenu1:List<WorkingPlatformOptionMenuItem> = listOf(),
    val platformsMenu2:List<WorkingPlatformOptionMenuItem> = listOf(),
    val showArchiveMenu:()->Unit={},
    val hideArchiveMenu:()->Unit={},
    val mainBarValue:Float=0f,
    val mainBarComparable:Float=0f,
    val subValue:Float=0f,
    val subComparable:Float=0f,
    val perDeliveryValue:Float=0f,
    val perDeliveryComparable:Float=0f,
    val perHourValue: Float=0f,
    val perHourComparable:Float=0f,
    val subSumAverageIncome:Float=0f,
    val subSumComparableAverageIncome:Float=0f
)
