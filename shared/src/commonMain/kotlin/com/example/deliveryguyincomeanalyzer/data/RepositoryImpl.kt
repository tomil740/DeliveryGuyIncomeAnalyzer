package com.example.deliveryguyincomeanalyzer.data

import com.example.deliveryguyincomeanalyzer.data.util.mapers.dataPerHourDataToDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.remoteWorkingPlatformToDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.shiftDataToShiftDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.shiftDomainToShiftData
import com.example.deliveryguyincomeanalyzer.data.util.mapers.workingPlatformDataToDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.workingPlatformDomainToData
import com.example.deliveryguyincomeanalyzer.database.WorkData
import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.RemoteWorkingPlatformDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getPlatformIdComponents
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.WorkDeclare
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

class RepositoryImpl(db: WorkData):Repository {
    private val dao = db.workDataQueries


    override fun insertLiveBuilderState(theState: LiveBuilderState) {
        //at every update of the builder state we will erase / replace all of it
        dao.deleteLiveDeliveryItems()
        dao.insertLiveBuilderState(
            startTime = theState.startTime.toString(),
            endTime = theState.endTime.toString(),
            totalTime = theState.totalTime.toDouble(),
            workingPlatformId = theState.workingPlatform,
            baseWage = theState.baseWage.toDouble(),
            extras = theState.extras.toDouble(),
            delivers = theState.delivers.toLong()
        )
        for (i in theState.deliversItem) {
            dao.insertLiveDeliveryItem(
                i.time.toString(), extra = i.extra.toDouble()
            )
        }
    }

    override fun getLiveBuilderState(): LiveBuilderState {
        val a = dao.getLiveBuilderState().executeAsOneOrNull()
        val b = dao.getLiveDeliveryItems().executeAsList()
        val c = mutableListOf<LiveDeliveryItem>()

        for (i in b) {
            c.add(
                LiveDeliveryItem(time = LocalDateTime.parse(i.time), extra = i.extra.toFloat())
            )
        }

        if (a != null) {
            return LiveBuilderState(
                baseWage = a.baseWage.toFloat(),
                startTime = LocalDateTime.parse(a.startTime),
                endTime = LocalDateTime.parse(a.endTime),
                totalTime = a.totalTime.toFloat(),
                workingPlatform = a.workingPlatformId,
                extras = a.extras.toFloat(),
                delivers = a.delivers.toInt(),
                deliversItem = c
            )
        }
        return LiveBuilderState(
            baseWage = 30f,
            startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            totalTime = 0f,
            workingPlatform = "Wolt-Center",
            extras = 0f,
            delivers = 0,
            deliversItem = listOf<LiveDeliveryItem>()
        )
    }

    override fun deleteLiveBuilderState() {
        dao.deleteLiveBuilderState()
        dao.deleteLiveDeliveryItems()
    }

    override fun insertShift(shift: ShiftDomain, workDeclareId: String): Long {
        val theShift = shiftDomainToShiftData(shift, workDeclareId = workDeclareId)
        dao.insertShift(
            baseIncome = theShift.baseIncome,
            shiftType = theShift.shiftType,
            time = theShift.time,
            extraIncome = theShift.extraIncome,
            deliveries = theShift.deliveries,
            workDeclareId = theShift.workDeclareId,
            startTime = shift.startTime.toString(),
            endTime = shift.endTime.toString()
        )
        return dao.getLastShift().executeAsOne().shiftId
    }

    override fun insertDataPerHour(
        dataPerHourDomain: DataPerHourDomain,
        workDeclareId: String,
        shiftId: Long
    ) {
        dao.insertDataPerHour(
            workDeclareId = workDeclareId,
            tip = dataPerHourDomain.extraIncome.toDouble(),
            base = dataPerHourDomain.baseIncome.toDouble(),
            deliveries = dataPerHourDomain.delivers.toDouble(),
            hour = dataPerHourDomain.hour.toString(),
            //if there is no related shift object we will insert 0 that wont connect anywhere...
            shiftId = shiftId
        )
    }

    override fun insertWorkDeclare(workDeclare: SumObjectInterface) {
        dao.insertWorkDeclare(
            workDeclareId = workDeclare.startTime.toString(),
            endDateTime = workDeclare.endTime.toString(),
            baseIncome = workDeclare.baseIncome.toDouble(),
            deliveries = workDeclare.delivers.toLong(),
            time = workDeclare.totalTime.toDouble(),
            extraIncome = workDeclare.extraIncome.toDouble(),
            month = workDeclare.startTime.year.toString() + workDeclare.startTime.month.number,
            workingPlatformId = workDeclare.platform
        )
    }

    override fun insertRemoteWorkingPlatforms(list: List<RemoteWorkingPlatformDomain>) {
        for (i in list) {
            dao.insertRemoteWorkingPlatform(
                workingPlatformId = "${i.platformName}-${i.workZone}",
                workingZone = i.workZone,
                platformName = i.platformName
            )
        }
    }

    override fun insertWorkingPlatform(workingPlatform: WorkingPlatform) {
        var isCustom = 0
        if(dao.getRemoteWorkingPlatformById(workingPlatformId = "${getPlatformIdComponents(workingPlatform.name).platformName}-X").executeAsOneOrNull() == null){
            isCustom=1
        }

        val theObj = workingPlatformDomainToData(workingPlatform, isCustom = isCustom)

        if(isCustom==1&&dao.getWorkingPlatformById("${getPlatformIdComponents(workingPlatform.name).platformName}-X").executeAsOneOrNull()==null){
            dao.insertWorkingPlatform(
                workingPlatformId="${getPlatformIdComponents(workingPlatform.name).platformName}-X",
                workingZone="X",
                platformName=theObj.platformName,
                isCustom=theObj.isCustom,
                morningShifts=theObj.morningShifts,
                morningShiftE =theObj.morningShiftE,
                noonShiftS=theObj.noonShiftS,
                noonShiftE=theObj.noonShiftE,
                eveningShiftS=theObj.eveningShiftS,
                eveningShiftE=theObj.eveningShiftE,
                baseSalary=theObj.baseSalary
            )
        }


        dao.insertWorkingPlatform(
            workingPlatformId=theObj.workingPlatformId,
            workingZone=theObj.workingZone,
        platformName=theObj.platformName,
        isCustom=theObj.isCustom,
        morningShifts=theObj.morningShifts,
        morningShiftE =theObj.morningShiftE,
        noonShiftS=theObj.noonShiftS,
        noonShiftE=theObj.noonShiftE,
        eveningShiftS=theObj.eveningShiftS,
        eveningShiftE=theObj.eveningShiftE,
        baseSalary=theObj.baseSalary
        )
    }

    override fun getLastWorkSession(): WorkSumDomain {
        val workDeclare = dao.getLastWorkDeclare().executeAsOne()
        val shifts =
            dao.getShiftsByWorkDeclareId(workDeclareId = workDeclare.workDeclareId).executeAsList()
        val workDeclareDataPerHour =
            dao.getDataPerHourByWorkDeclareId(workDeclareId = workDeclare.workDeclareId)
                .executeAsList()

        val resultShifts = mutableListOf<ShiftDomain>()

        for (i in shifts) {
            val dataPerHour = dao.getDataPerHourByShiftId(i.shiftId).executeAsList()
            resultShifts.add(
                shiftDataToShiftDomain(i, workDeclare.workingPlatformId, dataPerHourDataToDomain(dataPerHour))
            )
        }

        return WorkSumDomain(
            startTime = LocalDateTime.parse(workDeclare.workDeclareId),
            endTime = LocalDateTime.parse(workDeclare.endDateTime),
            time = workDeclare.time.toFloat(),
            deliveries = workDeclare.deliveries.toInt(),
            baseIncome = workDeclare.baseIncome.toFloat(),
            extraIncome = workDeclare.extraIncome.toFloat(),
            yearAndMonth = workDeclare.month,
            dayOfMonth = 7,
            workingPlatform = workDeclare.workingPlatformId,
            workPerHour = dataPerHourDataToDomain(workDeclareDataPerHour),
            shifts = resultShifts,
            objectsType = SumObjectsType.WorkSession,
            subObjects = listOf()
        )
    }

    private fun getDomainWorkDeclare(workDeclareParam: WorkDeclare): WorkSumDomain {
        val shifts = dao.getShiftsByWorkDeclareId(workDeclareId = workDeclareParam.workDeclareId)
            .executeAsList()
        val workDeclareDataPerHour =
            dao.getDataPerHourByWorkDeclareId(workDeclareId = workDeclareParam.workDeclareId)
                .executeAsList()

        val resultShifts = mutableListOf<ShiftDomain>()

        for (i in shifts) {
            val dataPerHour = dao.getDataPerHourByShiftId(i.shiftId).executeAsList()
            resultShifts.add(
                shiftDataToShiftDomain(i, workDeclareParam.workingPlatformId, dataPerHourDataToDomain(dataPerHour))
            )
        }

        return WorkSumDomain(
            startTime = LocalDateTime.parse(workDeclareParam.workDeclareId),
            endTime = LocalDateTime.parse(workDeclareParam.endDateTime),
            time = workDeclareParam.time.toFloat(),
            deliveries = workDeclareParam.deliveries.toInt(),
            baseIncome = workDeclareParam.baseIncome.toFloat(),
            extraIncome = workDeclareParam.extraIncome.toFloat(),
            yearAndMonth = workDeclareParam.month,
            dayOfMonth = 7,
            workingPlatform = workDeclareParam.workingPlatformId,
            workPerHour = dataPerHourDataToDomain(workDeclareDataPerHour),
            shifts = resultShifts,
            objectsType = SumObjectsType.WorkSession,
            subObjects = listOf()
        )
    }

    override fun getMonthSum(month: String,workingPlatform: String): List<WorkSumDomain> {
        val a =
        if(workingPlatform == "Any"){
            dao.getMonthData(month).executeAsList()
        }else {
            dao.getByPlatMonthData(theMonth = month, workingPlatform = workingPlatform)
                .executeAsList()
        }
        val theResult = mutableListOf<WorkSumDomain>()
        for (i in a) {
            val b = getDomainWorkDeclare(i)
            theResult.add(b)
        }

        return theResult
    }

    override fun getAllWorkDeclareData(workingPlatform: String): List<WorkSumDomain> {
        val a = if(workingPlatform == "Any"){
           dao.getAllWorkDeclareData().executeAsList()
        }else{
            dao.getAllWorkDeclareDataByPlatform(workingPlatform).executeAsList()
        }

        val theResult = mutableListOf<WorkSumDomain>()
        for (i in a) {
            val b = getDomainWorkDeclare(i)
            theResult.add(b)
        }

        return theResult
    }

    override fun getFirstWorkDeclareDate(): String {
        return dao.getFirstWorkDeclare().executeAsOne().month

    }

    override fun getAllShiftsByType(platform: String, theType: Int): List<WorkSumDomain> {
        val a = dao.getAllShiftDataByType(theType.toLong()).executeAsList()
        val theResult = mutableListOf<WorkSumDomain>()
        for (i in a) {
            val dataPerHour = dao.getDataPerHourByShiftId(i.shiftId).executeAsList()
            theResult.add(
                shiftDataToShiftDomain(
                    i,
                    platform,
                    dataPerHourDataToDomain(dataPerHour)
                ).toWorkSumDomain(platform)
            )
        }

        return theResult

    }

    override fun getCustomWorkingPlatformMenu(): Flow<List<WorkingPlatformOptionMenuItem>> {
        return dao.getLocalWorkingPlatformOptions1().asFlow().mapToList().flatMapConcat {
            val theResult = mutableListOf<WorkingPlatformOptionMenuItem>()
            for (i in it){
                val a = dao.getLocalWorkingPlatformOptions2(i.platformName).executeAsList().filter { it.workingZone!="X" }
                theResult.add(
                    WorkingPlatformOptionMenuItem(
                        platformName = i.platformName!!,
                        workingZones = a.map { it.workingZone!! }
                    )
                )
            }
             flow<List<WorkingPlatformOptionMenuItem>>{
                emit(theResult)
            }
        }
    }

    override fun getRemoteWorkingPlatformMenu(): Flow<List<WorkingPlatformOptionMenuItem>> {
        return dao.getRemoteWorkingPlatformOptions1().asFlow().mapToList().flatMapConcat {
            val theResult = mutableListOf<WorkingPlatformOptionMenuItem>()
            for (i in it) {
                val a = dao.getRemoteWorkingPlatformOptions2(i.platformName).executeAsList().filter { it.workingZone!="X" }
                theResult.add(
                    WorkingPlatformOptionMenuItem(
                        platformName = i.platformName!!,
                        workingZones = a.map { it.workingZone!! }
                    )
                )
            }
            flow<List<WorkingPlatformOptionMenuItem>> {
                emit(theResult)
            }
        }
    }

    override fun getWorkingPlatformById(platformId: String): WorkingPlatform? {
        val a = dao.getWorkingPlatformById(platformId).executeAsOneOrNull()
        return if (a != null) {
            workingPlatformDataToDomain(a)
        } else {
            null
        }
    }

    override fun getRemoteWorkingPlatformById(platformId: String): WorkingPlatform {
        val a = dao.getRemoteWorkingPlatformById(platformId).executeAsOne()
            return remoteWorkingPlatformToDomain(a)

    }
}












