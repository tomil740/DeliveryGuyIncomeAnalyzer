package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType

class GetWorkSessionStatisticsData (val repository: Repository) {
    operator fun invoke(workingPlatform: String): SumObjectInterface {
       val a = repository.getAllWorkDeclareData(workingPlatform)

        return SumDomainData().getAverageDomainObject(a, objectType = SumObjectsType.WorkSession.name) .toWorkSum(
            SumObjectSourceType.MyStatistics
        ).copy(objectName = "MyStatistics")

    }
}