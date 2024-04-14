package com.example.deliveryguyincomeanalyzer.domain.model.util

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain


fun getIncomeDataPerHour(workPerHour : List<DataPerHourDomain>):List<Float>{
    val result = mutableListOf<Float>()
    for (i in workPerHour)
        result.add(i.baseIncome+i.extraIncome)

    return result
}

fun getWorkDataPerHour(workPerHour : List<DataPerHourDomain>):List<Float>{
    val result = mutableListOf<Float>()
    for (i in workPerHour)
        result.add(i.delivers.toFloat())

    return result
}