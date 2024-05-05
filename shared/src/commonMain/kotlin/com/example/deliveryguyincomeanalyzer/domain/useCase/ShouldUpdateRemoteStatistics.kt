package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class ShouldUpdateRemoteStatistics(val repository: Repository) {
    operator fun invoke():List<String>{
        val b = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        //should be allwase as th 01 day so they could be compared
        val a = LocalDate(year = b.year, month = b.month, dayOfMonth = 1)
        val lastUpdateOg = mapToDateFormat(repository.getUserDataLastUpdate())
        val currentYearMonth = mapToDateFormat(subtractMonthToYearMonth("${a.year}${a.month.number}"))

        var lastUpdate = lastUpdateOg


        val resultList = mutableListOf<String>()
        //should be according to the all time (month is not taking in calculation years)
        while (lastUpdate < currentYearMonth){
            val ab = addMonthToYearMonth(lastUpdate.toString())
            if(mapToDateFormat(ab.toString()) <= currentYearMonth){
                lastUpdate = addMonthToYearMonth(lastUpdate.toString())
                resultList.add(lastUpdate.toString())
                lastUpdate = mapToDateFormat(lastUpdate.toString())

            }
        }

        return resultList

    }

}

fun mapToDateFormat(theVal:String):Int{
    if (theVal.length < 6){
        return "${theVal.substring(0,theVal.length-1)}0${theVal.substring(theVal.length-1)}".toInt()
    }
    return theVal.toInt()
}
fun addMonthToYearMonth(a:String):Int{
    val year = a.substring(0,4)//will be the year , must be the first 4 charecters .
    var month = a.substring(4)//from the same reason must be the end charecters after the year


    val monthToEnter = if(month.length > 1){month}else{"0$month"}

    val currentDate = LocalDate.parse("$year-$monthToEnter-01").plus(DatePeriod(months = 1))

    val resultYear:Int = currentDate.year
    val resultMonth:Int = currentDate.month.number

    return ("$resultYear$resultMonth").toInt()
}
fun subtractMonthToYearMonth(a:String):String{
    val year = a.substring(0,4)//will be the year , must be the first 4 charecters .
    var month = a.substring(4)//from the same reason must be the end charecters after the year


    val monthToEnter = if(month.length > 1){month}else{"0$month"}

    val currentDate = LocalDate.parse("$year-$monthToEnter-01").minus(DatePeriod(months = 1))

    val resultYear:Int = currentDate.year
    val resultMonth:Int = currentDate.month.number

    return ("$resultYear$resultMonth")
}