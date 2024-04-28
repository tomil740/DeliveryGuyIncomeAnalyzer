package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getWorkingPlatformShifts
import database.RemoteWorkingPlatform
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun remoteWorkingPlatformToDomain(dataObj:RemoteWorkingPlatform):WorkingPlatform {

    val b = getWorkingPlatformShifts(listOf(
        LocalTime(12,0),LocalTime(17,30),
        LocalTime(17,30),LocalTime(23,0),
        LocalTime(23,0),LocalTime(4,0),
    ))
    val a = listOf(
        ShiftFrame(
            name = "Morrning",
            startTime = b[0],
            endTime = b[1]
        ),
        ShiftFrame(
            name = "Noon",
            startTime = b[2],
            endTime = b[3]
        ),
        ShiftFrame(
            name = "Night",
            startTime =b[4],
            endTime = b[5]
        )
    )


    return WorkingPlatform(
        name = dataObj.platformName.toString(),
        shifts = a,
        baseWage = 30f
    )

}