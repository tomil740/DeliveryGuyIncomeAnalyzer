package com.example.deliveryguyincomeanalyzer.domain.model.theModels

import kotlinx.datetime.LocalDateTime

data class WorkingPlatform (
    val name : String,//the name will be as in the data base "platformName-workingZone" the '-' is in order of extrect
    //the platform name and the workZone from the name
    val shifts : List<ShiftFrame>,
    val baseWage : Float,
    val isDefault:Boolean=false
)

data class ShiftFrame(
    val name : String,
    //we will use date time to let us save hours when the day change in the night shift
    val startTime :LocalDateTime,
    val endTime : LocalDateTime
)