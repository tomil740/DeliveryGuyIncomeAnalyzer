package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain

class GetLastWorkSessionSum(val repository: Repository) {
    operator fun invoke():WorkSumDomain{
        return repository.getLastWorkSession()

    }

}