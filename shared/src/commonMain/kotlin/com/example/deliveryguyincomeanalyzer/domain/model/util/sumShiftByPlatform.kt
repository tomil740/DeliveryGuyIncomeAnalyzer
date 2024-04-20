package com.example.deliveryguyincomeanalyzer.domain.model.util

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftsSumByType
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

fun sumShiftsByPlatform(shifts : List<ShiftDomain>,dataPerHour:List<DataPerHourDomain>):List<ShiftsSumByType>{
        val shiftsSumMorning = mutableListOf<SumObj>()
        val shiftsSumNoon = mutableListOf<SumObj>()
        val shiftsSumNight = mutableListOf<SumObj>()

    //get new average data per hour


        shifts.forEach {
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
        //sum each shift type data
        val sumMorning: ShiftDomain
        var extraIncome: Float= 0f
        var baseIncome: Float = 0f
        var delivers:Float =0f
        var totalTime = 0f
        for (i in shiftsSumMorning){
           extraIncome+=i.extraIncome
            baseIncome+=i.baseIncome
            delivers+=i.delivers
            totalTime+=i.totalTime
        }
    //all of the hard coded data will be solve when implemnting the working platform object
        sumMorning = ShiftDomain(workingPlatform = ".", shiftType = "Morning", startTime = LocalDateTime(date = LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)), endTime =  LocalDateTime(LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)),
            time = totalTime/shiftsSumMorning.size,baseIncome/shiftsSumMorning.size,extraIncome/shiftsSumMorning.size,
            (delivers/shiftsSumMorning.size).toInt(), dataPerHour = dataPerHour
        )
      val sumNoon:ShiftDomain
         extraIncome = 0f
         baseIncome = 0f
         delivers =0f
         totalTime = 0f
    for (i in shiftsSumNoon){
        extraIncome+=i.extraIncome
        baseIncome+=i.baseIncome
        delivers+=i.delivers
        totalTime+=i.totalTime
    }
    sumNoon = ShiftDomain(workingPlatform = ".", shiftType = "Noon",startTime = LocalDateTime(date = LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)), endTime =  LocalDateTime(LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)),
        time = totalTime/shiftsSumMorning.size,baseIncome/shiftsSumMorning.size,extraIncome/shiftsSumMorning.size,
        (delivers/shiftsSumMorning.size).toInt(), dataPerHour = dataPerHour
    )
        val sumNight:ShiftDomain
        extraIncome = 0f
        baseIncome = 0f
        delivers =0f
        totalTime = 0f
    for (i in shiftsSumNight){
        extraIncome+=i.extraIncome
        baseIncome+=i.baseIncome
        delivers+=i.delivers
        totalTime+=i.totalTime
    }
        sumNight = ShiftDomain(workingPlatform = "Wolt", shiftType = "Night", startTime = LocalDateTime(date = LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)), endTime =  LocalDateTime(LocalDate(year = 2024, month = Month.APRIL,3), time =LocalTime( hour = 8, minute = 2)),
            time = totalTime/shiftsSumMorning.size,baseIncome/shiftsSumMorning.size,extraIncome/shiftsSumMorning.size,
            (delivers/shiftsSumMorning.size).toInt(), dataPerHour = dataPerHour
        )

    return listOf(ShiftsSumByType(type = "Morning", totalShifts = shiftsSumMorning.size, shiftSum = sumMorning.toShiftSum(), shiftSums = shiftsSumMorning),
            ShiftsSumByType(type = "Noon", totalShifts = shiftsSumNoon.size, shiftSum = sumNoon.toShiftSum(), shiftSums = shiftsSumNoon),
            ShiftsSumByType(type = "Night", totalShifts = shiftsSumNight.size, shiftSum = sumNight.toShiftSum(), shiftSums = shiftsSumNight))
    }
