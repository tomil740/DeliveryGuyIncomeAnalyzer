package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import kotlinx.coroutines.flow.Flow

class GetWorkingPlatformMenu(val repository: Repository) {
    operator fun invoke(isGeneralStat:Boolean=false):Flow<List<WorkingPlatformOptionMenuItem>>{

        if(!isGeneralStat)
            return repository.getCustomWorkingPlatformMenu()
        else {
            return repository.getRemoteWorkingPlatformMenu()
        }

    }

}