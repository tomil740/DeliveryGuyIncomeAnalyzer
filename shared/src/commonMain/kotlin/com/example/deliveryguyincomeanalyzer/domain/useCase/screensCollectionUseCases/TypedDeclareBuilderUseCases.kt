package com.example.deliveryguyincomeanalyzer.domain.useCase.screensCollectionUseCases

import com.example.deliveryguyincomeanalyzer.domain.useCase.DeleteLiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetAllTimeMonthData
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetDeclareShifts
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetLastWorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetLiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetLiveDeclareDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetRemoteDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetRemoteWorkDeclare
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetTypedDeclareDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkSessionStatisticsData
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkingPlatformById
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkingPlatformMenu
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertLiveDeliveryState
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertShiftObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertWorkDeclare
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateRemoteDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateRemoteWorkDeclare

data class TypedDeclareBuilderUseCases(
    val getTypedDeclareDataPerHour: GetTypedDeclareDataPerHour,
    val getDeclareShifts: GetDeclareShifts,
    val insertWorkDeclare: InsertWorkDeclare,
    val insertDataPerHour: InsertDataPerHour,
    val insertShiftObj: InsertShiftObj,
    val getWorkingPlatformById: GetWorkingPlatformById,
    val getWorkingPlatformMenu: GetWorkingPlatformMenu,
    val getAllTimeMonthData: GetAllTimeMonthData,
    val getWorkSessionStatisticsData: GetWorkSessionStatisticsData,

    val getRemoteWorkDeclare: GetRemoteWorkDeclare,
    val updateRemoteWorkDeclare: UpdateRemoteWorkDeclare,
    val getRemoteDataPerHour: GetRemoteDataPerHour,
    val updateRemoteDataPerHour: UpdateRemoteDataPerHour,


    //for testing only
    val getLastWorkSessionSum: GetLastWorkSessionSum
)
