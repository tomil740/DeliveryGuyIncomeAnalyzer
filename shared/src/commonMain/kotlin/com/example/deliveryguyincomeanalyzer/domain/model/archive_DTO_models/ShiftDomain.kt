package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.GraphState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getIncomeDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.model.util.getSumObjectHeader
import com.example.deliveryguyincomeanalyzer.domain.model.util.getWorkDataPerHour
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

data class ShiftDomain(
    val workingPlatform : String,
    val shiftType : String, //(will be connecting to the close group definition)
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val time : Float,
    val baseIncome : Float,
    val extraIncome : Float,
    val delivers : Int,
    val dataPerHour : List<DataPerHourDomain>
){
    fun toShiftSum(sumObjectSourceType:SumObjectSourceType =SumObjectSourceType.Archive): SumObj {

        return SumObj(
           objectName = getSumObjectHeader(objectType = SumObjectsType.ShiftSession,shiftType,startTime),
            shiftType=shiftType,
            startTime= startTime,
            endTime=endTime,
            totalTime = time,
            extraIncome = extraIncome,
            baseIncome =baseIncome,
            delivers = delivers,
            averageIncomePerHour = (baseIncome+extraIncome)/time,
            averageIncomePerDelivery = (baseIncome+extraIncome)/delivers,
            workPerHour = GraphState(ogLst = getWorkDataPerHour(dataPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            incomePerSpecificHour = GraphState(ogLst = getIncomeDataPerHour(dataPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            averageIncomeSubObj = 1f,
            platform = workingPlatform,
            objectType = SumObjectsType.ShiftSession,
            totalIncome = baseIncome+extraIncome,
            subObjName = "Shift",
            subObjects = null,
            shiftsSumByType = null,
            averageTimeSubObj = 1f,
            sumObjectSourceType = sumObjectSourceType

        )
    }

    fun toWorkSumDomain(workingPlatform: String=""): WorkSumDomain {
        return WorkSumDomain(
            startTime = startTime,
            endTime =endTime,
            time =time,
            deliveries = delivers,
            baseIncome = baseIncome,
            extraIncome = extraIncome,
            yearAndMonth =startTime.year.toString()+startTime.month.number.toString(),
            dayOfMonth = 7,
            workingPlatform =workingPlatform,
            workPerHour = dataPerHour,
            shifts = listOf(),
            objectsType = SumObjectsType.ShiftSession,
            subObjects = listOf()
        )
    }

}