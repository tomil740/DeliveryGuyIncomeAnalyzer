package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import database.WokingPlatform
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun workingPlatformDataToDomain(dataObject:WokingPlatform):WorkingPlatform {

   val a= listOf(
    ShiftFrame(
        name = "Morrning",
        startTime = LocalDateTime.parse(dataObject.morningShifts),
        endTime = LocalDateTime.parse(dataObject.morningShiftE)
    ),
    ShiftFrame(
        name = "Noon",
        startTime = LocalDateTime.parse(dataObject.noonShiftS),
        endTime = LocalDateTime.parse(dataObject.noonShiftE)
    ),
    ShiftFrame(
        name = "Night",
        startTime =  LocalDateTime.parse(dataObject.eveningShiftS),
        endTime = LocalDateTime.parse(dataObject.eveningShiftE)
    )
   )


    return WorkingPlatform(
        name = dataObject.workingPlatformId,
        shifts = a,
        baseWage = dataObject.baseSalary.toFloat()
    )

}
/*
fun setLocalDateTimeFrame(theTime:String):LocalDateTime{
    val currentTimeDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    val hour = if (theTime.length<=2){theTime.toInt()}else{theTime.substring(0,2).toInt()}
    val minute = if (theTime.length<=2){0}else{theTime.substring(2).toInt()}

    return LocalDateTime(date = currentTimeDate.date,
       time = LocalTime(hour = hour, minute = minute))
}

 */