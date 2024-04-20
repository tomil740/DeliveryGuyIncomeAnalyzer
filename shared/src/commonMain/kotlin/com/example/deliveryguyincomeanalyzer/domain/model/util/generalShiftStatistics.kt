package com.example.deliveryguyincomeanalyzer.domain.model.util

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftsSumByType
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj

fun getGeneralSum(data:List<ShiftsSumByType>): GeneralShiftStatistics {
    var baseObj: SumObj?=null
    var totalMorning: Int=0
    var totalNoon: Int=0
    var totalNight: Int=0
    //sum each shift type data
    val sumMorning: ShiftDomain
    var extraIncome: Float = 0f
    var baseIncome: Float = 0f
    var delivers: Float = 0f
    var totalTime = 0f
    for (i in data) {
        baseObj = i.shiftSum
        extraIncome += i.shiftSum.extraIncome
        baseIncome += i.shiftSum.baseIncome
        delivers += i.shiftSum.delivers
        totalTime += i.shiftSum.totalTime
        when(i.shiftSum.shiftType){
            "Morning"->{totalMorning=i.totalShifts}
            "Noon"->{totalNoon=i.totalShifts}
            "Night"->{totalNight=i.totalShifts}
        }
    }
    return GeneralShiftStatistics(
        shiftSum = baseObj!!.copy(shiftType = "Total",baseIncome=baseIncome/(data.size.toFloat()), extraIncome = extraIncome/data.size.toFloat(),
           delivers =  delivers.toInt()/data.size, totalTime = totalTime/(data.size.toFloat())),totalMorning=totalMorning,totalNoon=totalNoon,totalNight=totalNight
    )
}

data class GeneralShiftStatistics(
    val shiftSum: SumObj,
    val totalMorning:Int,
    val totalNoon:Int,
    val totalNight:Int
)