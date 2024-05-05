package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType

class GetWorkSessionStatisticsData (val repository: Repository) {
    operator fun invoke(workingPlatform: String): SumObjectInterface {
       val a = repository.getAllWorkDeclareData(workingPlatform)
        //val b = SumDomainData().getSummarizesDomainObject(a)

        return SumDomainData().getAverageDomainObject(a) .toWorkSum(
            SumObjectSourceType.MyStatistics
        ).copy(objectName = "MyStatistics")

    }
}