package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels.RemoteWorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.util.getLocalTimeAsFloat
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.datetime.LocalDateTime


/*
GetRemoteWorkDeclare :
this use case pull from the database the current matched object according to the parameter working platform
, at the repository function level the database object will be map to the matched domain one
and that will be return from the use case...
 */
class GetRemoteWorkDeclare(val repository: Repository) {
    operator fun invoke(workingPlatform:String):RemoteWorkDeclareDomain{
       return repository.getRemoteWorkDeclareByPlatformId(workingPlatform)

    }
}