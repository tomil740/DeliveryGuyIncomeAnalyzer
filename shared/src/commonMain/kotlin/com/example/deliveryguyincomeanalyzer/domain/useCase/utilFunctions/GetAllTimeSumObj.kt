package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions

import com.example.deliveryguyincomeanalyzer.data.util.mapers.dataPerHourDataToDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.useCase.MonthSumData
import com.example.deliveryguyincomeanalyzer.domain.useCase.SumDomainData
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
/*
getAllTimeSumObj :
this function will get from the use case and the repository all of the month sum data in a collections matched to each month
calculate them and return the overall sum Sum Object of all of this data and with the sub data sumarisise as well
*if there is no match data (we got empty list as parameter)*
we will return fake empty sumObj with the empty obj mark , total time -1 ...
in order of mark this object to the UI as empty ...

 */

fun getAllTimeSumObj(a: List<MonthSumData>,workingPlat:String?=null):SumObjectInterface{
    //store the data sum we will generate from each month workSession data list
    val sumObjList = mutableListOf<WorkSumDomain>()
    for (month in a){
        //sum the all month object data
        if(month.data.isEmpty()){
            /*
            sumObjList.add(
                WorkSumDomain(
                    startTime = LocalDateTime(LocalDate(year = month.month.substring(0,4).toInt(), month =  Month(month.month.substring(4).toInt()), dayOfMonth = 3), time = LocalTime(12,12)),
                    endTime = LocalDateTime(LocalDate(year = month.month.substring(0,4).toInt(), month =  Month(month.month.substring(4).toInt()), dayOfMonth = 3), time = LocalTime(12,12)),
                    time = -1f,
                    deliveries =1,
                    baseIncome = 1f,
                    extraIncome = 1f,
                    yearAndMonth = month.month,
                    dayOfMonth = 1,
                    workingPlatform = workingPlat ?: "",
                    workPerHour = listOf(),
                    shifts = listOf(),
                    objectsType = SumObjectsType.MonthSum,
                    subObjects = listOf()
                )
            )

             */
        }else {
            val currentMonthSum = SumDomainData().getSummarizesDomainObject(month.data)
            sumObjList.add(currentMonthSum)
        }
    }
    //here we at the total sum that sums up all of the months sum
    return SumDomainData().getSummarizesDomainObject(sumObjList, objectType = SumObjectsType.AllTimeSum).toWorkSum().copy(platform = workingPlat?:"")
}
