package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.isShiftSession
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getDataPerHourSum
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

class SumDomainData() {

    fun getSummarizesDomainObject(a: List<WorkSumDomain>,objectType:SumObjectsType = SumObjectsType.MonthSum,workingPlat:String = "Any"): WorkSumDomain {
        var time: Float = 0f
        var deliveries: Int = 0
        var baseIncome: Float = 0f
        var extraIncome: Float = 0f
        val dataPerHour = mutableListOf<DataPerHourDomain>()
        val shifts = mutableListOf<ShiftDomain>()

        if(a.isEmpty()){
          return  WorkSumDomain(
                startTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                endTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                time = -1f,
                deliveries =1,
                baseIncome = 1f,
                extraIncome = 1f,
                yearAndMonth ="22222",
                dayOfMonth = 1,
                workingPlatform = workingPlat ?: "",
                workPerHour = listOf(),
                shifts = listOf(),
                objectsType = SumObjectsType.MonthSum,
                subObjects = listOf()
            )
        }

        var workingPlatform = a[0].workingPlatform

        for (i in a) {
            if(i.workingPlatform!=workingPlatform)
                workingPlatform="Any"
            time += i.time
            deliveries += i.deliveries
            baseIncome += i.baseIncome
            extraIncome += i.extraIncome
            //calculate dat PerHour
            for (j in i.workPerHour) {
                dataPerHour.add(j)
            }
            for (k in i.shifts)
                shifts.add(k)
        }

        val currentTime = a.first().startTime
        val ab = WorkSumDomain(
            startTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 1
                ), time = LocalTime(0, 0)
            ),
            endTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 28
                ), time = LocalTime(0, 0)
            ),
            time = time,
            deliveries = deliveries,
            baseIncome = baseIncome,
            extraIncome = extraIncome,
            yearAndMonth = a.get(0).yearAndMonth,
            dayOfMonth = 1,
            workingPlatform = workingPlatform,
            workPerHour = getDataPerHourSum(dataPerHour),
            shifts = shifts,
            objectsType = objectType,
            subObjects = a
        )



        return ab

    }

    fun getAverageDomainObject(a: List<WorkSumDomain>,objectType:String = SumObjectsType.MonthSum.name,workingPlat: String=""): WorkSumDomain {
        var time: Float = 0f
        var deliveries: Int = 0
        var baseIncome: Float = 0f
        var extraIncome: Float = 0f
        val dataPerHour = mutableListOf<DataPerHourDomain>()
        val shifts = mutableListOf<ShiftDomain>()

        if(a.isEmpty()){
            return  WorkSumDomain(
                startTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                endTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                time = -1f,
                deliveries =1,
                baseIncome = 1f,
                extraIncome = 1f,
                yearAndMonth ="22222",
                dayOfMonth = 1,
                workingPlatform = workingPlat ?: "",
                workPerHour = listOf(),
                shifts = listOf(),
                objectsType = SumObjectsType.MonthSum,
                subObjects = listOf()
            )
        }
        val theObjectType = isShiftSession(objectType)
        var workingPlatform = a[0].workingPlatform
        //for shift time frame data ...
        var startTime=LocalTime(0, 0)
        var endTime =  LocalTime(23, 0)
        if (theObjectType==SumObjectsType.ShiftSession){
            startTime = a.first().startTime.time
            endTime = a.first().endTime.time
        }
        for (i in a) {

            if(i.startTime.time<startTime)
                startTime = i.startTime.time

            if(i.endTime.time > endTime)
                endTime = i.endTime.time

            if(i.workingPlatform !=workingPlatform)
                workingPlatform = "Any"

            time += i.time
            deliveries += i.deliveries
            baseIncome += i.baseIncome
            extraIncome += i.extraIncome
            //calculate dat PerHour
            for (j in i.workPerHour) {
                dataPerHour.add(j)
            }
            for (k in i.shifts)
                shifts.add(k)
        }


        val currentTime = a[0].startTime
        val ab = WorkSumDomain(
            startTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 1
                ), time = startTime
            ),
            endTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 1
                ), time =endTime
            ),
            time = time / a.size,
            deliveries = deliveries / a.size,
            baseIncome = baseIncome / a.size,
            extraIncome = extraIncome / a.size,
            yearAndMonth = a.get(0).yearAndMonth,
            dayOfMonth = 1,
            workingPlatform = workingPlatform,
            workPerHour = getDataPerHourSum(dataPerHour,startTime=startTime.hour,endTime=endTime.hour),
            shifts = shifts,
            objectsType = theObjectType,
            shiftType = if(theObjectType.name != objectType){objectType}else{null},
            subObjects = a
        )



        return ab

    }
}