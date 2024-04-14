package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository

class DeleteLiveBuilderState(val repository: Repository) {

    operator fun invoke(){
        repository.deleteLiveBuilderState()
    }
}