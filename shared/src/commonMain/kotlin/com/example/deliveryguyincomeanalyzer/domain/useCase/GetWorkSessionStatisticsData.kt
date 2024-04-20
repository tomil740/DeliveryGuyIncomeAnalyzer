package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

class GetWorkSessionStatisticsData (val repository: Repository) {
    operator fun invoke(workingPlatform: String): SumObjectInterface {
       val a = repository.getAllWorkDeclareData()
        val b= SumDomainData().getSummarizesDomainObject(a)

        return SumDomainData().getAverageDomainObject(a) .toWorkSum(

        ).copy(objectName = "MyStatistics")

    }
}