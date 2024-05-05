package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteWorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.util.getLocalTimeAsFloat
import kotlinx.datetime.LocalDateTime

class UpdateRemoteWorkDeclare(val repository: Repository) {
    operator fun invoke(currentObj:RemoteWorkDeclareDomain,sTime:LocalDateTime,eTime:LocalDateTime){

        var startTime : Float = currentObj.startTime*(currentObj.theCounter.toFloat())
        var endTime : Float = currentObj.endTime*(currentObj.theCounter.toFloat())

        if(sTime.date != eTime.date){
            endTime+=24
        }

        endTime+= getLocalTimeAsFloat(eTime.time)
        startTime+= getLocalTimeAsFloat(sTime.time)

        val theStartTime = startTime/(currentObj.theCounter.toFloat()+1f)
        val theEndTime = endTime/(currentObj.theCounter.toFloat()+1f)

        repository.insertRemoteWorkDeclare(
            currentObj.copy(startTime = theStartTime, endTime = theEndTime, theCounter = currentObj.theCounter+1)
            )

    }

}