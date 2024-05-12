package com.example.deliveryguyincomeanalyzer.domain.useCase

data class DeclareBuilderUseCases(
    val insertLiveDeliveryState: InsertLiveDeliveryState,
    val getLiveDeliveryState: GetLiveBuilderState,
    val deleteLiveBuilderState:DeleteLiveBuilderState,
    val getLiveDeclareDataPerHour: GetLiveDeclareDataPerHour,
    val getDeclareShifts: GetDeclareShifts,
    val insertWorkDeclare: InsertWorkDeclare,
    val insertDataPerHour: InsertDataPerHour,
    val insertShiftObj: InsertShiftObj,
    val getWorkingPlatformById: GetWorkingPlatformById,
    val getWorkingPlatformMenu:GetWorkingPlatformMenu,
    val getAllTimeMonthData:GetAllTimeMonthData,
    val getWorkSessionStatisticsData: GetWorkSessionStatisticsData,

    val getRemoteWorkDeclare: GetRemoteWorkDeclare,
    val updateRemoteWorkDeclare: UpdateRemoteWorkDeclare,
    val getRemoteDataPerHour: GetRemoteDataPerHour,
    val updateRemoteDataPerHour: UpdateRemoteDataPerHour,


    //for testing only
    val getLastWorkSessionSum: GetLastWorkSessionSum
)
