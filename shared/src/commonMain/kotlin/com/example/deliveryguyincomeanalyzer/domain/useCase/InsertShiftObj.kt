package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain

/*
InsertShiftObj : ^
this function will get as parameter an shift object data (ready to insert)
and insert it and return the new object id ...
 */
class InsertShiftObj(val repository: Repository) {
    suspend operator fun invoke(shiftObject:ShiftDomain,workDeclareId:String):Long {
        return repository.insertShift(shiftObject,workDeclareId)
    }
}