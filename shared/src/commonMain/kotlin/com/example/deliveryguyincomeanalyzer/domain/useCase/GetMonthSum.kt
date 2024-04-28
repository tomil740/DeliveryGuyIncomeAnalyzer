package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getDataPerHourSum
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class GetMonthSum(val repository: Repository) {

    operator fun invoke(yearAndMonth:String,workingPlatform: String): SumObj {
        val a = repository.getMonthSum(yearAndMonth,workingPlatform)

        var time : Float=0f
        var deliveries : Int=0
        var baseIncome : Float=0f
        var extraIncome : Float=0f
        val dataPerHour = mutableListOf<DataPerHourDomain>()
        val shifts = mutableListOf<ShiftDomain>()


        for (i in a){
            time+=i.time
            deliveries+=i.deliveries
            baseIncome+=i.baseIncome
            extraIncome+=i.extraIncome
            //calculate dat PerHour
            for (j in i.workPerHour) {
                dataPerHour.add(j)
            }
            for (k in i.shifts)
                shifts.add(k)
        }

        val currentTime = a[0].startTime
       val ab = WorkSumDomain(
            startTime = LocalDateTime(LocalDate(year = currentTime.year, month = currentTime.month, dayOfMonth = 1), time= LocalTime(0,0)),
            endTime = LocalDateTime(LocalDate(year = currentTime.year, month = currentTime.month, dayOfMonth = 28), time= LocalTime(23,0)),
            time = time,
            deliveries = deliveries,
            baseIncome = baseIncome,
            extraIncome = extraIncome,
            yearAndMonth = a.get(0).yearAndMonth,
            dayOfMonth = 1,
            workingPlatform = a.get(0).workingPlatform,
            workPerHour = getDataPerHourSum(dataPerHour),
            shifts = shifts,
           objectsType = SumObjectsType.MonthSum,
           subObjects = a
        )

        val subObj = mutableListOf<SumObj>()
        for (u in a){
            subObj.add(u.toWorkSum())
        }


        return ab.toWorkSum()

    }

}