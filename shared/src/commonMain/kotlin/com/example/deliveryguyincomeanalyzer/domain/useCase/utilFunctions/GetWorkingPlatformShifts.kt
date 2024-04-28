package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.plus

fun getWorkingPlatformShifts(shiftsData:List<LocalTime>):List<LocalDateTime>{

    var endTime:Int = shiftsData[0].hour.minus(1)

    val theResult = mutableListOf<LocalDateTime>()
    val fakeData = LocalDate(year = 2024, month = Month.APRIL, dayOfMonth = 3)

    for (i in shiftsData) {
        if (i.hour > endTime) {
            theResult.add(LocalDateTime(date = fakeData, time = i))
        } else {
            theResult.add(LocalDateTime(date = fakeData.plus(DatePeriod(days = 1)), time = i))
        }
    }
    return theResult

}