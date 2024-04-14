package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

class GetLiveBuilderState(val repository: Repository) {
    suspend operator fun invoke():LiveBuilderState{
        return repository.getLiveBuilderState()
    }
}