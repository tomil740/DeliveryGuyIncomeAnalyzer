package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteWorkDeclareDomain

class GetRemoteDataPerHour(val repository: Repository) {
    operator fun invoke (workingPlatformId : String ,size:Int,startH:Int):List<RemoteDataPerHourDomain>{
        val hoursList = mutableListOf<Int>()
        var theTime =startH
        repeat(size){
            if(theTime >= 24)
                theTime = 0

            hoursList.add(theTime)

            theTime+=1
        }

        val theResult = mutableListOf<RemoteDataPerHourDomain>()

        for (i in hoursList){
            theResult.add(repository.getRemoteDataPerHourById("$workingPlatformId-$i"))
        }
        return theResult
    }

}