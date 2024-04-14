package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import database.Shift
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.getShiftType
import kotlinx.datetime.LocalTime

//when the workingPlatform will be an object , it will include all of the hard coded data
fun shiftDataToShiftDomain(shift: Shift,workingPlatform: String,workPerHour : List<DataPerHourDomain>):ShiftDomain{
    return ShiftDomain(
        workingPlatform = workingPlatform,
        shiftType = getShiftType(shift.shiftType.toInt()),
        startTime = LocalTime(12,3),
        endTime = LocalTime(12,3),
        time = shift.time.toFloat(),
        baseIncome = shift.baseIncome.toFloat(),
        extraIncome = shift.extraIncome.toFloat(),
        delivers = shift.deliveries.toInt(),
        dataPerHour = workPerHour,

    )
}