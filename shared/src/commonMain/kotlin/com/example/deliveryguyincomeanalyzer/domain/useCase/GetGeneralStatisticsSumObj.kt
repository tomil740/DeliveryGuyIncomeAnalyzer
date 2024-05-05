package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteWorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getLocalDateTimeFromFloatTime
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus



/*
for the base an shifts attributes we put an fake values those will be update in the vm function with other data
* workingPlatformId
* baseIncome
* shifts
As well as values that will not be in use to generalStatistics like spesific dataes
 */
class GetGeneralStatisticsSumObj(val repository: Repository) {

    operator fun invoke(remoteWorkDeclare:RemoteWorkDeclareDomain,remoteDataPerHourDomainList: List<RemoteDataPerHourDomain>):WorkSumDomain{

        var theEndTime = getLocalDateTimeFromFloatTime(remoteWorkDeclare.endTime)
        var theStartTime = getLocalDateTimeFromFloatTime(remoteWorkDeclare.startTime)
        if (remoteWorkDeclare.startTime <= remoteWorkDeclare.endTime)
            theEndTime = LocalDateTime(date = theEndTime.date.plus(DatePeriod(days = 1)), time = theEndTime.time)

        var deliveries = 0
        var extras = 0f
        for (i in remoteDataPerHourDomainList){
            deliveries+=i.dataPerHourDomain.delivers
            extras+=i.dataPerHourDomain.extraIncome
        }

        if(theStartTime.minute != 0){
            val matchData = remoteDataPerHourDomainList.first().dataPerHourDomain
            val theShare = theStartTime.minute/60f

            extras+=theShare*matchData.extraIncome
            deliveries+=(theShare*matchData.delivers).toInt()
        }
        if(theEndTime.minute != 0){
            val matchData = remoteDataPerHourDomainList.last().dataPerHourDomain
            val theShare = theEndTime.minute/60f

            extras+=theShare*matchData.extraIncome
            deliveries+=(theShare*matchData.delivers).toInt()
        }


       return WorkSumDomain(
             objectsType= SumObjectsType.WorkSession,
             startTime = theStartTime,
             endTime = theEndTime,
             time = remoteWorkDeclare.totalTime,
             deliveries = deliveries,
             baseIncome = 1f,
             extraIncome = extras,
             yearAndMonth = "2044",
             dayOfMonth = 36,
             workingPlatform = "Any",
             workPerHour = remoteDataPerHourDomainList.map { it.dataPerHourDomain },
             shiftType= null,
             shifts = listOf(),
             subObjects= listOf()
        )




    }


}