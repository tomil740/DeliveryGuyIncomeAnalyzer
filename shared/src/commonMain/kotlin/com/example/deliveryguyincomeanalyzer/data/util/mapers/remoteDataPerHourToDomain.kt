package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain
import database.RemoteDataPerHour

fun remoteDataPerHourToDomain(theObj:RemoteDataPerHour):RemoteDataPerHourDomain{
    val a = DataPerHourDomain(
        hour = theObj.hour.toInt(), extraIncome = theObj.extra.toFloat(),
        baseIncome = theObj.base.toFloat(), delivers = theObj.deliveries.toInt()
    )
    return RemoteDataPerHourDomain(a,theObj.counter.toInt())
}
/*
workingPlatformId
workingZone
 */