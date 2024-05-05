package com.example.deliveryguyincomeanalyzer.domain.useCase

data class ObjectItemUseCases(
    val getLastWorkSessionSum: GetLastWorkSessionSum,
    val getMonthSum:GetMonthSum,
    val getAllTimeMonthData:GetAllTimeMonthData,
    val getWorkSessionStatisticsData:GetWorkSessionStatisticsData,
    val sumDomainData:SumDomainData,
    val getShiftTypeStatisticsData:GetShiftTypeStatisticsData,
    val getWorkingPlatformMenu:GetWorkingPlatformMenu,
    val getRemoteWorkDeclare: GetRemoteWorkDeclare,
    val getRemoteDataPerHour: GetRemoteDataPerHour,
    val getGeneralStatisticsSumObj: GetGeneralStatisticsSumObj,
    val getDeclareShifts: GetDeclareShifts,
    val getWorkingPlatformById: GetWorkingPlatformById,
    val shouldUpdateRemoteStatistics: ShouldUpdateRemoteStatistics,
    val getUserRemoteWorkingPlatformsList: GetUserRemoteWorkingPlatformsList,
    val getMonthWorkDeclareAmount:GetMonthWorkDeclareAmount,
    val updateRemoteSumObj:UpdateRemoteSumObj,
    val updateUserData:UpdateUserData,
    val getTopLevelGeneralStatisticsSumObj:GetTopLevelGeneralStatisticsSumObj
)
