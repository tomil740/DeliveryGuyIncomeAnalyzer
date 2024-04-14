package com.example.deliveryguyincomeanalyzer.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class ShiftsSum(
    val totalShifts : Int ,
    val shiftSums : List<ShiftSum>
)

data class ShiftSum(
    val shiftType : String,//(defined the matched type from the three)
    val startTime : LocalTime ,
    val endTime : LocalTime ,
    val totalTime : Float ,
    val incomeExtra : Float ,
    val incomeBase : Float ,
    val delivers : Int,
    val perDelivery : Float ,
    val perHour : Float ,
    val workPerHour : GraphState?=null,//(this object includes the List <Flaot>  value data) ,
    val incomePerHour : GraphState?=null//(this object includes the List <Flaot>  value data)
)
