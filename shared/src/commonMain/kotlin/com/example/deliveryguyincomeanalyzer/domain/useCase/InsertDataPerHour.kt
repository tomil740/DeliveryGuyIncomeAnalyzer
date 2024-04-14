package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain

/*
InsertDataPerHour : ^
this function get as parameters all the related object id and the object data , insert it to the matched db entity
with the matched supplied forgain key (if supply to it )

 */
class InsertDataPerHour(val repository: Repository) {
    suspend operator fun invoke(dataPerHourDomain: DataPerHourDomain, workDeclareId:String, shiftId:Long){
        repository.insertDataPerHour(dataPerHourDomain,workDeclareId,shiftId)
    }
}