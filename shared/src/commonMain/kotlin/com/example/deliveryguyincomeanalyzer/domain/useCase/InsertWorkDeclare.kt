package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum

/*
InsertWorkDeclare
Will get the builder that data (that all most matched to the basic declare entity object attributes)
will implement those in the entity attribute and return the ned declare entity object id
 */
class InsertWorkDeclare(val repository: Repository) {
    suspend operator fun invoke(theDeclare: WorkSessionSum) {
        repository.insertWorkDeclare(theDeclare)
    }
}
