package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models

import com.example.deliveryguyincomeanalyzer.domain.model.GraphState
import com.example.deliveryguyincomeanalyzer.domain.model.ShiftSum
import com.example.deliveryguyincomeanalyzer.domain.model.util.getIncomeDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.model.util.getWorkDataPerHour
import kotlinx.datetime.LocalTime

data class ShiftDomain(
    val workingPlatform : String,
    val shiftType : String, //(will be connecting to the close group definition)
    val startTime : LocalTime,
    val endTime : LocalTime,
    val time : Float,
    val baseIncome : Float,
    val extraIncome : Float,
    val delivers : Int,
    val dataPerHour : List<DataPerHourDomain>
){
    fun toShiftSum(): ShiftSum {
       return ShiftSum(
            shiftType=shiftType,
            startTime=startTime,
            endTime=endTime,
            totalTime = time,
            incomeExtra = extraIncome,
            incomeBase =baseIncome,
            delivers = delivers,
            perHour = (baseIncome+extraIncome)/time,
            perDelivery = (baseIncome+extraIncome)/delivers,
            workPerHour = GraphState(ogLst = getIncomeDataPerHour(dataPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            incomePerHour = GraphState(ogLst = getWorkDataPerHour(dataPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
        )
    }

}