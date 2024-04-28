package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.RemoteWorkingPlatformDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getWorkingPlatformShifts
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class GetAllTimeMonthData(val repository: Repository) {

    operator fun invoke(workingPlatform: String):List<MonthSumData>{
      //  insertRemoteWorkingPlatforms()
        val startMont = repository.getFirstWorkDeclareDate()
        var startTime = LocalDate(year = startMont.substring(0,4).toInt(), month = Month(startMont.substring(4).toInt()), dayOfMonth = 28)
        val theResultObjects = mutableListOf<MonthSumData>()
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        while (if(startTime.year==currentTime.year){startTime.month<=currentTime.month}else{
            startTime < currentTime}){
            val a =  repository.getMonthSum("${startTime.year}${startTime.month.number}",workingPlatform)
            if(a.isNotEmpty()){
            theResultObjects.add(MonthSumData(month = "${startTime.year}${startTime.month.number}",
                data =a))
            }
            startTime=startTime.plus(DatePeriod(months = 1))
        }

        return theResultObjects
    }

    fun insertRemoteWorkingPlatforms(){

        repository.insertRemoteWorkingPlatforms(
            listOf(
                RemoteWorkingPlatformDomain("X","Wolt"),
                RemoteWorkingPlatformDomain("X","Atza"),
                RemoteWorkingPlatformDomain("X","Dominos"),

                RemoteWorkingPlatformDomain("North","Wolt"),
                RemoteWorkingPlatformDomain("South","Wolt"),
                RemoteWorkingPlatformDomain("Center","Wolt"),
                RemoteWorkingPlatformDomain("North","Atza"),
                RemoteWorkingPlatformDomain("South","Atza"),
                RemoteWorkingPlatformDomain("Center","Atza"),
                RemoteWorkingPlatformDomain("North","Dominos"),
                RemoteWorkingPlatformDomain("South","Dominos"),
                RemoteWorkingPlatformDomain("Center","Dominos"),))

        val b = getWorkingPlatformShifts(listOf(
            LocalTime(12,0),LocalTime(17,30),
            LocalTime(17,30),LocalTime(23,0),
            LocalTime(23,0),LocalTime(4,0),
        ))
        val a = listOf(
            ShiftFrame(
                name = "Morrning",
                startTime = b[0],
                endTime = b[1]
            ),
            ShiftFrame(
                name = "Noon",
                startTime = b[2],
                endTime = b[3]
            ),
            ShiftFrame(
                name = "Night",
                startTime =b[4],
                endTime = b[5]
            )
        )
        repository.insertWorkingPlatform(
           workingPlatform =
            WorkingPlatform("Aruma-Center",a,45f)
       )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Aruma-South",a,45f)
        )

        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Wolt-North",a,60f)
        )




    }
}

data class MonthSumData(
    val month:String,
    val data:List<WorkSumDomain>
)