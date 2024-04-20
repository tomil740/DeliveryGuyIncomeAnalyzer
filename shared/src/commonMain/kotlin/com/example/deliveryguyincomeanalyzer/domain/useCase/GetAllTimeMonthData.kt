package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class GetAllTimeMonthData(val repository: Repository) {

    operator fun invoke(workingPlatform: String):List<MonthSumData>{

        val startMont = repository.getFirstWorkDeclareDate()
        var startTime = LocalDate(year = startMont.substring(0,4).toInt(), month = Month(startMont.substring(4).toInt()), dayOfMonth = 28)
        val theResultObjects = mutableListOf<MonthSumData>()
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        while (if(startTime.year==currentTime.year){startTime.month<=currentTime.month}else{
            startTime < currentTime}){
            theResultObjects.add(MonthSumData(month = "${startTime.year}${startTime.month.number}",
                data = repository.getMonthSum("${startTime.year}${startTime.month.number}")
            ))
            startTime=startTime.plus(DatePeriod(months = 1))
        }

        return theResultObjects
    }
}

data class MonthSumData(
    val month:String,
    val data:List<WorkSumDomain>
)