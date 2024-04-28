package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform

data class DeclareBuilderUseCases(
    val insertLiveDeliveryState: InsertLiveDeliveryState,
    val getLiveDeliveryState: GetLiveBuilderState,
    val deleteLiveBuilderState:DeleteLiveBuilderState,
    val getDeclareDataPerHour: GetDeclareDataPerHour,
    val getDeclareShifts: GetDeclareShifts,
    val insertWorkDeclare: InsertWorkDeclare,
    val insertDataPerHour: InsertDataPerHour,
    val insertShiftObj: InsertShiftObj,
    val getWorkingPlatformById: GetWorkingPlatformById,
    val getWorkingPlatformMenu:GetWorkingPlatformMenu,

    //for testing only
    val getLastWorkSessionSum: GetLastWorkSessionSum
)
