package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteSumObjectStatisticsDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteWorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.RemoteWorkingPlatformDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.getPlatformIdComponents
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getWorkingPlatformShifts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
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

    operator fun invoke(workingPlatform: String): List<MonthSumData> {
       //insertRemoteWorkingPlatforms()
        //overRideData()
        val startMont = repository.getFirstWorkDeclareDate()
        var startTime = LocalDate(
            year = startMont.substring(0, 4).toInt(),
            month = Month(startMont.substring(4).toInt()),
            dayOfMonth = 28
        )
        val theResultObjects = mutableListOf<MonthSumData>()
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        while (if (startTime.year == currentTime.year) {
                startTime.month <= currentTime.month
            } else {
                startTime < currentTime
            }
        ) {
            val a = repository.getMonthSum(
                "${startTime.year}${startTime.month.number}",
                workingPlatform
            )
            if (a.isNotEmpty()) {
                theResultObjects.add(
                    MonthSumData(
                        month = "${startTime.year}${startTime.month.number}",
                        data = a
                    )
                )
            }
            startTime = startTime.plus(DatePeriod(months = 1))
        }

        return theResultObjects

    }
    fun overRideData(){
        val a = LocalDate(2024, Month.FEBRUARY, 22)
        repository.updateUserData("${a.year}${a.month.number}")


        val theList = listOf("Wolt-Center","Wolt-North","Dominos-North","Dominos-Center","Dominos-South")
        for (i in theList) {
            repository.insertRemoteSumObjectStatistics(
                RemoteSumObjectStatisticsDomain(
                    workingPlatformId = i,
                    monthAmount = 0,
                    totalMonths = 0,
                    yearAmount = 0,
                    totalYears = 0
                )
            )
        }


    }

    fun insertRemoteWorkingPlatforms() {
/*
        repository.insertRemoteWorkingPlatforms(
            listOf(
                RemoteWorkingPlatformDomain("X", "Wolt"),
                RemoteWorkingPlatformDomain("X", "Atza"),
                RemoteWorkingPlatformDomain("X", "Dominos"),

                RemoteWorkingPlatformDomain("North", "Wolt"),
                RemoteWorkingPlatformDomain("South", "Wolt"),
                RemoteWorkingPlatformDomain("Center", "Wolt"),
                RemoteWorkingPlatformDomain("North", "Atza"),
                RemoteWorkingPlatformDomain("South", "Atza"),
                RemoteWorkingPlatformDomain("Center", "Atza"),
                RemoteWorkingPlatformDomain("North", "Dominos"),
                RemoteWorkingPlatformDomain("South", "Dominos"),
                RemoteWorkingPlatformDomain("Center", "Dominos"),
            )
        )

        val b = getWorkingPlatformShifts(
            listOf(
                LocalTime(12, 0), LocalTime(17, 30),
                LocalTime(17, 30), LocalTime(23, 0),
                LocalTime(23, 0), LocalTime(4, 0),
            )
        )
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
                startTime = b[4],
                endTime = b[5]
            )
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Aruma-Center", a, 45f)
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Aruma-South", a, 45f)
        )

        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Wolt-North", a, 60f)
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Wolt-Center", a, 80f)
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Dominos-Center", a, 60f)
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Dominos-North", a, 60f)
        )
        repository.insertWorkingPlatform(
            workingPlatform =
            WorkingPlatform("Dominos-South", a, 60f)
        )
*/


        //initialize the remote tables
        CoroutineScope(Dispatchers.IO).launch {
            //Todo this is not legal according to the use case
            val theLst = GetUserRemoteWorkingPlatformsList(repository).invoke()

            val a = LocalDate(2023, Month.AUGUST, 22)
            repository.updateUserData("${a.year}${a.month.number}")

            for (i in theLst) {
                repository.insertRemoteWorkDeclare(
                    remoteWorkDeclareDomain = RemoteWorkDeclareDomain(
                        startTime = 4f,
                        endTime = 23.5f,
                        theCounter = 1,
                        totalTime = (23.5f - 4f),
                        workingPlatformId = i
                    )
                )
                repository.insertRemoteSumObjectStatistics(
                    RemoteSumObjectStatisticsDomain(
                        workingPlatformId = i,
                        monthAmount = 1,
                        totalMonths = 1,
                        yearAmount = 1,
                        totalYears = 1
                    )
                )


                for (t in 0..23) {
                    val theId = getPlatformIdComponents(i)
                    repository.insertRemoteDataPerHour(
                        RemoteDataPerHourDomain(
                            dataPerHourDomain = DataPerHourDomain(
                                baseIncome = 35f, extraIncome = 40f,
                                delivers = 6, hour = t
                            ),
                            totalObjects = 1,
                        ), workingPlatform = theId.platformName, workingZone = theId.workingZone
                    )
                }
            }
        }

    }




    data class MonthSumData(
        val month: String,
        val data: List<WorkSumDomain>
    )
}