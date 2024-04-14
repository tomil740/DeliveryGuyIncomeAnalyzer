package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.Platform
import com.example.deliveryguyincomeanalyzer.domain.model.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import kotlinx.datetime.LocalTime

class GetPlatformByName {
    suspend operator fun invoke(platformName: String){//:WorkingPlatform {

        //we will use the  repository function in the future
     //  return WorkingPlatform
    }
}

