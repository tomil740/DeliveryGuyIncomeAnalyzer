package com.example.deliveryguyincomeanalyzer.domain.useCase

data class ObjectItemUseCases(
    val getLastWorkSessionSum: GetLastWorkSessionSum,
    val getMonthSum:GetMonthSum,
    val getAllTimeMonthData:GetAllTimeMonthData,
    val getWorkSessionStatisticsData:GetWorkSessionStatisticsData,
    val sumDomainData:SumDomainData,
    val getShiftTypeStatisticsData:GetShiftTypeStatisticsData,
    val getWorkingPlatformMenu:GetWorkingPlatformMenu
)
