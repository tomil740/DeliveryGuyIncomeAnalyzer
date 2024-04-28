package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform


/*
GetWorkingPlatformById:
will get the picked working platform id from the picker option menu ,
then pull from the data base the idale matched platofrm object
whether a local override or the default values according to the workingPlatform const
*if its a default values objects the return value will notice the UI*
 */
class GetWorkingPlatformById(val repository: Repository) {
    operator fun invoke(workingPlatformId:String):WorkingPlatform{

        val a = repository.getWorkingPlatformById(workingPlatformId)
        return a ?: repository.getRemoteWorkingPlatformById(workingPlatformId).copy(isDefault = true)

    }
}