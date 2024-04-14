package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

class InsertLiveDeliveryState(val repository: Repository) {
    suspend operator fun invoke(live_DeliveryState: LiveBuilderState){
        repository.insertLiveBuilderState(live_DeliveryState)
    }

}