package com.example.deliveryguyincomeanalyzer.domain.model.util

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftsSumByType
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.ShiftTypes
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

fun sumShiftsByPlatform(shifts : List<ShiftDomain>,dataPerHour:List<DataPerHourDomain>):List<ShiftsSumByType>{
        val shiftsSumMorning = mutableListOf<SumObj>()
        val shiftsSumNoon = mutableListOf<SumObj>()
        val shiftsSumNight = mutableListOf<SumObj>()


    //get new average data per hour
    val theData = shifts.filter { it.startTime!=it.endTime }

    var a:ShiftsSumByType?=null
    var b:ShiftsSumByType?=null
    var c:ShiftsSumByType?=null

    theData.forEach {
            when (it.shiftType) {
                "Morning" -> {
                    shiftsSumMorning.add(
                        it.toShiftSum())
                }

                "Noon" -> {
                    shiftsSumNoon.add(it.toShiftSum())
                }

                "Night" -> {
                    shiftsSumNight.add(it.toShiftSum())
                }
            }
        }
    val sumMorning: ShiftDomain
    var extraIncome: Float = 1f
    var baseIncome: Float = 1f
    var delivers: Float = 1f
    var totalTime = 1f
    if(shiftsSumMorning.isNotEmpty()) {
        //sum each shift type data
        for (i in shiftsSumMorning) {
            extraIncome += i.extraIncome
            baseIncome += i.baseIncome
            delivers += i.delivers
            totalTime += i.totalTime
        }
        //all of the hard coded data will be solve when implemnting the working platform object
        sumMorning = ShiftDomain(
            workingPlatform = shiftsSumMorning.first().platform,
            shiftType = "Morning",
            startTime = shiftsSumMorning.first().startTime,
            endTime = shiftsSumMorning.last().endTime,
            time = totalTime / shiftsSumMorning.size,
            baseIncome / shiftsSumMorning.size,
            extraIncome / shiftsSumMorning.size,
            (delivers / shiftsSumMorning.size).toInt(),
            dataPerHour = dataPerHour
        )
        a =ShiftsSumByType(type = ShiftTypes.Morning, totalShifts = shiftsSumMorning.size, shiftSum = sumMorning.toShiftSum(), shiftSums = shiftsSumMorning)

    }
    if(shiftsSumNoon.isNotEmpty()) {
        extraIncome = 1f
        baseIncome = 1f
        delivers = 1f
        totalTime = 1f
        for (i in shiftsSumNoon) {
            extraIncome += i.extraIncome
            baseIncome += i.baseIncome
            delivers += i.delivers
            totalTime += i.totalTime
        }
        val sumNoon: ShiftDomain = ShiftDomain(
            workingPlatform = shiftsSumNoon.first().platform,
            shiftType = "Noon",
            startTime = shiftsSumNoon.first().startTime,
            endTime = shiftsSumNoon.last().endTime,
            time = totalTime / shiftsSumNoon.size,
            baseIncome / shiftsSumNoon.size,
            extraIncome / shiftsSumNoon.size,
            (delivers / shiftsSumNoon.size).toInt(),
            dataPerHour = dataPerHour
        )
        b = ShiftsSumByType(type = ShiftTypes.Noon, totalShifts = shiftsSumNoon.size, shiftSum = sumNoon.toShiftSum(), shiftSums = shiftsSumNoon)

    }
    if(shiftsSumNight.isNotEmpty()) {
        extraIncome = 1f
        baseIncome = 1f
        delivers = 1f
        totalTime = 1f
        for (i in shiftsSumNight) {
            extraIncome += i.extraIncome
            baseIncome += i.baseIncome
            delivers += i.delivers
            totalTime += i.totalTime
        }
        val sumNight: ShiftDomain = ShiftDomain(
            workingPlatform = shiftsSumNight.first().platform,
            shiftType = "Night",
            startTime = shiftsSumNight.first().startTime,
            endTime = shiftsSumNight.last().endTime,
            time = totalTime / shiftsSumNight.size,
            baseIncome / shiftsSumNight.size,
            extraIncome / shiftsSumNight.size,
            (delivers / shiftsSumNight.size).toInt(),
            dataPerHour = dataPerHour
        )
        c =  ShiftsSumByType(type = ShiftTypes.Night, totalShifts = shiftsSumNight.size, shiftSum = sumNight.toShiftSum(), shiftSums = shiftsSumNight)

    }


    val result = mutableListOf<ShiftsSumByType>()

    if(a?.totalShifts !=null && a.totalShifts!=0)
        result.add(a)
    if(b?.totalShifts !=null && b.totalShifts!=0)
        result.add(b)
    if(c?.totalShifts !=null && c.totalShifts!=0)
        result.add(c)

    return result
    }
