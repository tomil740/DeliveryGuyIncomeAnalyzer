package com.example.deliveryguyincomeanalyzer.domain.useCase

data class DeclareBuilderUseCases(
    val insertLiveDeliveryState: InsertLiveDeliveryState,
    val getLiveDeliveryState: GetLiveBuilderState,
    val deleteLiveBuilderState:DeleteLiveBuilderState,
    val getDeclareDataPerHour: GetDeclareDataPerHour,
    val getDeclareShifts: GetDeclareShifts,
    val insertWorkDeclare: InsertWorkDeclare,
    val insertDataPerHour: InsertDataPerHour,
    val insertShiftObj: InsertShiftObj,


    //for testing only
    val getLastWorkSessionSum: GetLastWorkSessionSum
)
