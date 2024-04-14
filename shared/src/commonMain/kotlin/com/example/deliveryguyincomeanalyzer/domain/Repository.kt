package com.example.deliveryguyincomeanalyzer.domain

import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

interface Repository {

    fun insertLiveBuilderState(theState: LiveBuilderState)

    fun getLiveBuilderState():LiveBuilderState

    fun deleteLiveBuilderState()

    fun insertShift(shift: ShiftDomain,workDeclareId:String):Long

    fun insertDataPerHour(dataPerHourDomain: DataPerHourDomain, workDeclareId: String, shiftId : Long)

    fun insertWorkDeclare(workDeclare: WorkSessionSum)

    fun getLastWorkSession():WorkDeclareDomain

}