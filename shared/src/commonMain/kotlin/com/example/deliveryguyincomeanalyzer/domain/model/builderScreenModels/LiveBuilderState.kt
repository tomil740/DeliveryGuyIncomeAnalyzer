package com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.datetime.LocalDateTime

data class LiveBuilderState(
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val totalTime : Float,
    val workingPlatform: String,
    val baseWage : Float,
    val totalBase : Float = baseWage*totalTime,
    val extras : Float ,
    val delivers : Int,
    val deliversItem: List<LiveDeliveryItem>
){
    fun toWorkSessionSum(sumObjectSourceType: SumObjectSourceType = SumObjectSourceType.Archive): SumObj {
        val theTime = getTimeDifferent(startTime =  startTime.time, endTime =  endTime.time)
        val baseIncome = baseWage*theTime

        return SumObj(startTime =  startTime, endTime = endTime, objectName = "Builder Data",
            totalTime = theTime, platform = workingPlatform, baseIncome = baseIncome
            , extraIncome = extras, totalIncome = baseIncome+extras
            , delivers = delivers, averageIncomePerDelivery = (baseIncome+extras)/delivers,
            averageIncomePerHour = (baseIncome+extras)/theTime, objectType = SumObjectsType.WorkSession, shiftType = null,
            averageIncomeSubObj = 1f, subObjName = "Sum", averageTimeSubObj = 1f,
            sumObjectSourceType = sumObjectSourceType)
    }
}
