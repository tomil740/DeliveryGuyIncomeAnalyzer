package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteSumObjectStatisticsDomain
import database.RemoteSumObjectStatistics

fun remoteSumObjectStatisticsToDomain(theObj:RemoteSumObjectStatistics):RemoteSumObjectStatisticsDomain{
    return RemoteSumObjectStatisticsDomain(
        workingPlatformId = theObj.workingPlatformId,
        monthAmount = theObj.monthDeclaries.toInt(),
        yearAmount = theObj.yearDeclaries.toInt(),
        totalYears = theObj.totalYears.toInt(),
        totalMonths = theObj.totalMonths.toInt()
    )
}