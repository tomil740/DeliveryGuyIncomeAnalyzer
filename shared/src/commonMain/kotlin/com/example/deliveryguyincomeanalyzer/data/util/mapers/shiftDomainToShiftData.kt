package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectIntType

fun shiftDomainToShiftData(shift: ShiftDomain,workDeclareId:String):database.Shift {

    val shiftType:Long = getSumObjectIntType(shift.shiftType).toLong()

    return database.Shift(
        baseIncome = shift.baseIncome.toDouble(), shiftType = shiftType, time = shift.time.toLong(),
        extraIncome = shift.extraIncome.toDouble(), deliveries = shift.delivers.toLong(), workDeclareId = workDeclareId, shiftId = 0,
        startTime = shift.startTime.toString(), endTime = shift.endTime.toString(), workingPlatform = shift.workingPlatform
    )
}