package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteSumObjectStatisticsDomain

class UpdateRemoteSumObj(val repository: Repository) {
    operator fun invoke(workingPlatform:String,newAmount:Int,monthAmount:Int){
        val currentObj = repository.getRemoteSumObjectStatisticsByWp(workingPlatform)

        var allMonthAmount = currentObj.monthAmount*currentObj.totalMonths
        allMonthAmount+=newAmount

        //Todo from some resaon when implemnt only with curly bracutes the result stay 0 ...
        val a = (allMonthAmount/(currentObj.totalMonths+monthAmount))

        val theIObj = RemoteSumObjectStatisticsDomain(
            //the plus one is because work Declaries will be netural number only and with this kind of calculation we most likely get some data lost
            monthAmount = a+1,
            totalMonths = currentObj.totalMonths+monthAmount,
            totalYears = currentObj.totalYears,
            yearAmount = currentObj.yearAmount,
            workingPlatformId = currentObj.workingPlatformId
            )

        repository.insertRemoteSumObjectStatistics(theIObj)

    }
}