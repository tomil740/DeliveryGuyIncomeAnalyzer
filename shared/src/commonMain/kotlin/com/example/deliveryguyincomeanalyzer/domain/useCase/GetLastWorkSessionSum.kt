package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkDeclareDomain

class GetLastWorkSessionSum(val repository: Repository) {
    operator fun invoke():WorkDeclareDomain{
        return repository.getLastWorkSession()

    }

}