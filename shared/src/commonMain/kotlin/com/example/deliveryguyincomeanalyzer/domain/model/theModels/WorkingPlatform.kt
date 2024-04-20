package com.example.deliveryguyincomeanalyzer.domain.model.theModels

import kotlinx.datetime.LocalDateTime

data class WorkingPlatform (
    val name : String,
    val shifts : List<ShiftFrame>,
    val baseWage : Float
)

data class ShiftFrame(
    val name : String,
    //we will use date time to let us save hours when the day change in the night shift
    val startTime :LocalDateTime,
    val endTime : LocalDateTime
)