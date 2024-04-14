package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models

import com.example.deliveryguyincomeanalyzer.domain.model.GraphState
import com.example.deliveryguyincomeanalyzer.domain.model.ShiftSum
import com.example.deliveryguyincomeanalyzer.domain.model.ShiftsSum
import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.model.util.getIncomeDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.model.util.getWorkDataPerHour
import kotlinx.datetime.LocalDateTime

data class WorkDeclareDomain(
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
    val shifts : List<ShiftDomain>
){
    fun toWorkSessionSum():WorkSessionSum{
        return WorkSessionSum(
            platform = workingPlatform,
            typeName= yearAndMonth + dayOfMonth,
            startTime= startTime,
            endTime=endTime,
            totalTime=time,
            totalIncome=baseIncome+extraIncome,
           baseIncome=baseIncome,
      extraIncome=extraIncome,
        delivers=deliveries,
        averageIncomePerHour= (baseIncome+extraIncome)/time ,
       averageIncomePerDelivery=(baseIncome+extraIncome)/deliveries,
        shiftsSum=   getDomainShiftsSum(),
       workPerHour=  GraphState(ogLst = getWorkDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour),
       incomePerSpecificHour=GraphState(ogLst = getIncomeDataPerHour(workPerHour), listStartTime = startTime.hour, listEndTime = endTime.hour)
        )
    }

    fun getDomainShiftsSum():ShiftsSum{

        val shiftsSum = mutableListOf<ShiftSum>()
        shifts.forEach {
            shiftsSum.add(it.toShiftSum()) }

        return ShiftsSum(
            totalShifts = shifts.size, shiftSums = shiftsSum
        )
    }
}
