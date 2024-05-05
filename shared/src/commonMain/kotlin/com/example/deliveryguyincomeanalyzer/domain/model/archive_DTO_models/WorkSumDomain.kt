package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.GraphState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getIncomeDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.model.util.getSumObjectHeader
import com.example.deliveryguyincomeanalyzer.domain.model.util.getWorkDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.model.util.sumShiftsByPlatform
import kotlinx.datetime.LocalDateTime

data class WorkSumDomain(
    val objectsType: SumObjectsType,
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val time : Float,
    val deliveries : Int,
    val baseIncome : Float,
    val extraIncome : Float,
    val yearAndMonth : String,
    val dayOfMonth : Int,
    val workingPlatform : String,
    val workPerHour : List<DataPerHourDomain>,
    val shiftType:String?=null,
    val shifts : List<ShiftDomain>,
    val subObjects: List<WorkSumDomain>
){
    fun toWorkSum(sumObjectSourceType: SumObjectSourceType = SumObjectSourceType.Archive): SumObj {
        return SumObj(
            platform = workingPlatform,
            objectName= getSumObjectHeader(objectsType, shiftType = null,startTime),
            startTime= startTime,
            endTime=endTime,
            totalTime=time,
            totalIncome=baseIncome+extraIncome,
           baseIncome=baseIncome,
      extraIncome=extraIncome,
        delivers=deliveries,
        averageIncomePerHour= (baseIncome+extraIncome)/time ,
       averageIncomePerDelivery=(baseIncome+extraIncome)/deliveries,
        shiftsSumByType=   sumShiftsByPlatform(shifts, dataPerHour = workPerHour),
            subObjects = if(objectsType==SumObjectsType.WorkSession){shifts.map { it.toShiftSum() }}else{subObjects.map { it.toWorkSum() }},
       workPerHour=  GraphState(ogLst = getWorkDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
       incomePerSpecificHour= GraphState(ogLst = getIncomeDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            averageIncomeSubObj = if(objectsType==SumObjectsType.WorkSession){((baseIncome+extraIncome)/shifts.size)}
    else{((baseIncome+extraIncome)/subObjects.size)},
            averageTimeSubObj = time/subObjects.size,
            objectType =objectsType,
            shiftType = null,//because workSumSomain dosnt work with shifts...
            subObjName = "Shift",
            sumObjectSourceType = sumObjectSourceType
        )
    }
/*
    fun toShiftSum(): SumObj {
        return SumObj(
            objectName = getSumObjectHeader(objectType = SumObjectsType.ShiftSession,shiftType,startTime),
            shiftType=shiftType,
            startTime= startTime,
            endTime=endTime,
            totalTime = time,
            extraIncome = extraIncome,
            baseIncome =baseIncome,
            delivers = deliveries,
            averageIncomePerHour = (baseIncome+extraIncome)/time,
            averageIncomePerDelivery = (baseIncome+extraIncome)/deliveries,
            workPerHour = GraphState(ogLst = getIncomeDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            incomePerSpecificHour = GraphState(ogLst = getWorkDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
            averageIncomeSubObj = 5f,
            platform = workingPlatformId,
            objectType = SumObjectsType.ShiftSession,
            totalIncome = baseIncome+extraIncome,
            subObjName = "Shift",
            subObjects = null,
            shiftsSumByType = null

        )
    }



 */
}
