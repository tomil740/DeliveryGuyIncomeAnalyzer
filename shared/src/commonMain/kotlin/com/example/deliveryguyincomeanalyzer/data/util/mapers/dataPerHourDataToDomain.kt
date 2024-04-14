package com.example.deliveryguyincomeanalyzer.data.util.mapers

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import database.DataPerHour

fun dataPerHourDataToDomain(dataPerHourArgu: List<DataPerHour>):List<DataPerHourDomain>{
    val result = mutableListOf<DataPerHourDomain>()
    for (dataPerHour in dataPerHourArgu) {
        result.add(DataPerHourDomain(
            hour = dataPerHour.hour.toInt(), extraIncome = dataPerHour.tip.toFloat(),
            baseIncome = dataPerHour.base.toFloat(), delivers = dataPerHour.deliveries.toInt()
        )
        )
    }
    return  result
}