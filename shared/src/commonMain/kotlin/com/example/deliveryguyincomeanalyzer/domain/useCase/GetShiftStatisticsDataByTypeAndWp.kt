package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectType

class GetShiftStatisticsDataByTypeAndWp (val repository: Repository) {
    operator fun invoke(workingPlatform: String,shiftType:Int): SumObjectInterface {

       val a =  repository.getAllShiftsByType(workingPlatform, theType = shiftType)

        return SumDomainData().getAverageDomainObject(a=a, objectType = getSumObjectType(shiftType),workingPlatform) .toWorkSum(SumObjectSourceType.MyStatistics).copy(objectName = "MyStatistics")


    }
}