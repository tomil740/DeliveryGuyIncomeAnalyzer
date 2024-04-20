package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.useCase.MonthSumData
import com.example.deliveryguyincomeanalyzer.domain.useCase.SumDomainData

fun getAllTimeSumObj(a: List<MonthSumData>):SumObjectInterface{
    //store the data sum we will generate from each month workSession data list
    val sumObjList = mutableListOf<WorkSumDomain>()
    for (month in a){
        //sum the all month object data
        val currentMonthSum =SumDomainData().getSummarizesDomainObject(month.data)
        sumObjList.add(currentMonthSum)
    }
    //here we at the total sum that sums up all of the months sum
    return SumDomainData().getSummarizesDomainObject(sumObjList, objectType = SumObjectsType.AllTimeSum).toWorkSum()
}
