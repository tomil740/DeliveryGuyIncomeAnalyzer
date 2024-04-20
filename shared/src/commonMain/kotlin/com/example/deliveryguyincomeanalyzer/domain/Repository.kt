package com.example.deliveryguyincomeanalyzer.domain

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

interface Repository {

    fun insertLiveBuilderState(theState: LiveBuilderState)

    fun getLiveBuilderState():LiveBuilderState

    fun deleteLiveBuilderState()

    fun insertShift(shift: ShiftDomain,workDeclareId:String):Long

    fun insertDataPerHour(dataPerHourDomain: DataPerHourDomain, workDeclareId: String, shiftId : Long)

    fun insertWorkDeclare(workDeclare: SumObjectInterface)

    fun getLastWorkSession():WorkSumDomain

    fun getMonthSum(month:String):List<WorkSumDomain>

    fun getAllWorkDeclareData():List<WorkSumDomain>

    fun getFirstWorkDeclareDate():String

    fun getAllShiftsByType(platform: String,theType:Int):List<WorkSumDomain>

}