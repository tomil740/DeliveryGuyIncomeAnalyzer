package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import database.WokingPlatform

fun workingPlatformDomainToData(workingPlatform: WorkingPlatform,isCustom:Int):WokingPlatform {

    val platformName = workingPlatform.name.substring(0,workingPlatform.name.indexOf('-'))
    val workingZone =  workingPlatform.name.substring(workingPlatform.name.indexOf('-')+1)

    return WokingPlatform(
        workingPlatformId = workingPlatform.name,
        workingZone = workingZone,
        platformName = platformName,
        isCustom = isCustom.toLong(),
        morningShifts = workingPlatform.shifts[0].startTime.toString(),
        morningShiftE =workingPlatform.shifts[0].endTime.toString(),
        noonShiftS = workingPlatform.shifts[1].startTime.toString(),
        noonShiftE = workingPlatform.shifts[1].endTime.toString(),
        eveningShiftS = workingPlatform.shifts[2].startTime.toString(),
        eveningShiftE = workingPlatform.shifts[2].endTime.toString(),
        baseSalary = workingPlatform.baseWage.toDouble()
    )

}