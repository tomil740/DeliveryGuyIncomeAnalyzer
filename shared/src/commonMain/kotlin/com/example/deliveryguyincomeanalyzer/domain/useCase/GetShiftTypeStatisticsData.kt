package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

class GetShiftTypeStatisticsData (val repository: Repository) {
    operator fun invoke(workingPlatform: String,shiftType:Int): SumObjectInterface {

       val a =  repository.getAllShiftsByType(workingPlatform, theType = shiftType)

        return SumDomainData().getAverageDomainObject(a) .toWorkSum().copy(objectName = "MyStatistics")


    }
}