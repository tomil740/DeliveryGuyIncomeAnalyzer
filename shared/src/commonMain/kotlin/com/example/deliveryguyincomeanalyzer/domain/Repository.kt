package com.example.deliveryguyincomeanalyzer.domain

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.RemoteWorkingPlatformDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun insertLiveBuilderState(theState: LiveBuilderState)

    fun getLiveBuilderState():LiveBuilderState

    fun deleteLiveBuilderState()

    fun insertShift(shift: ShiftDomain,workDeclareId:String):Long

    fun insertDataPerHour(dataPerHourDomain: DataPerHourDomain, workDeclareId: String, shiftId : Long)
    fun insertWorkDeclare(workDeclare: SumObjectInterface)
    fun insertRemoteWorkingPlatforms(list: List<RemoteWorkingPlatformDomain>)
    fun insertWorkingPlatform(workingPlatform: WorkingPlatform)
    fun getLastWorkSession():WorkSumDomain

    fun getMonthSum(month:String,workingPlatform:String):List<WorkSumDomain>

    fun getAllWorkDeclareData(workingPlatform:String):List<WorkSumDomain>

    fun getFirstWorkDeclareDate():String

    fun getAllShiftsByType(platform: String,theType:Int):List<WorkSumDomain>
    fun getRemoteWorkingPlatformMenu():Flow<List<WorkingPlatformOptionMenuItem>>

    fun getCustomWorkingPlatformMenu(): Flow<List<WorkingPlatformOptionMenuItem>>
    fun getWorkingPlatformById(platformId:String):WorkingPlatform?
    fun getRemoteWorkingPlatformById(platformId:String):WorkingPlatform


    /*
    will pull all of the platforms list from the remote table...
    pull all of the platforms list from the local table...
    then marge them into one list according to an uneqe id (with the N check)
     */

}