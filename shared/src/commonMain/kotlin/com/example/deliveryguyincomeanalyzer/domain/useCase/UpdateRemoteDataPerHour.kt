package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteDataPerHourDomain

class UpdateRemoteDataPerHour(val repository: Repository) {
    operator fun invoke(workingPlatform:String,workingZone:String,currentObj:List<RemoteDataPerHourDomain>,theObj:List<DataPerHourDomain>) {

        for (i in currentObj.indices){

            val currentObject = currentObj[i]
            val theObject = theObj[i]

            var deliveries: Float =
                currentObject.dataPerHourDomain.delivers * currentObject.totalObjects.toFloat()
            var extras = currentObject.dataPerHourDomain.extraIncome * currentObject.totalObjects
            var base = currentObject.dataPerHourDomain.baseIncome * currentObject.totalObjects

            deliveries += theObject.delivers
            extras += theObject.extraIncome
            base += theObject.baseIncome

            val theCounter = currentObject.totalObjects + 1f

            deliveries /= theCounter
            extras /= theCounter
            base /= theCounter

            val newObj = RemoteDataPerHourDomain(
                dataPerHourDomain = DataPerHourDomain(
                    currentObject.dataPerHourDomain.hour,
                    extras,
                    base,
                    deliveries.toInt()
                ), theCounter.toInt()
            )

            repository.insertRemoteDataPerHour(
                newObj,
                workingPlatform = workingPlatform,
                workingZone = workingZone
            )

        }

    }
}