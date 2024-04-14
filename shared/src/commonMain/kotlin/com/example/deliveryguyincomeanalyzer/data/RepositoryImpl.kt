package com.example.deliveryguyincomeanalyzer.data

import com.example.deliveryguyincomeanalyzer.data.util.mapers.dataPerHourDataToDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.shiftDataToShiftDomain
import com.example.deliveryguyincomeanalyzer.data.util.mapers.shiftDomainToShiftData
import com.example.deliveryguyincomeanalyzer.database.WorkData
import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkDeclareDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

class RepositoryImpl(db: WorkData):Repository {
    val dao = db.workDataQueries


    override fun insertLiveBuilderState(theState: LiveBuilderState) {
        //at every update of the builder state we will erase / replace all of it
        dao.deleteLiveDeliveryItems()
        dao.insertLiveBuilderState(
            startTime = theState.startTime.toString(),
            endTime = theState.endTime.toString(),
            totalTime = theState.totalTime.toDouble(),
            workingPlatform = theState.workingPlatform,
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
        println("the eeeee:$a")
        val b = dao.getLiveDeliveryItems().executeAsList()
        val c = mutableListOf<LiveDeliveryItem>()

        for (i in b) {
            c.add(
                LiveDeliveryItem(time = LocalDateTime.parse(i.time), extra = i.extra.toFloat())
            )
        }

        if (a != null) {
            println("not null")
            return LiveBuilderState(
                baseWage = a.baseWage.toFloat(),
                startTime = LocalDateTime.parse(a.startTime),
                endTime = LocalDateTime.parse(a.endTime),
                totalTime = a.totalTime.toFloat(),
                workingPlatform = a.workingPlatform,
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
            workingPlatform = "Wolt",
            extras = 0f,
            delivers = 0,
            deliversItem = listOf<LiveDeliveryItem>()
        )
    }

    override fun deleteLiveBuilderState() {
        dao.deleteLiveBuilderState()
        dao.deleteLiveDeliveryItems()
    }

    override fun insertShift(shift: ShiftDomain,workDeclareId:String): Long {
       val theShift = shiftDomainToShiftData(shift, workDeclareId = workDeclareId)
        dao.insertShift(
            baseIncome = theShift.baseIncome, shiftType = theShift.shiftType, time = theShift.time,
            extraIncome = theShift.extraIncome, deliveries = theShift.deliveries, workDeclareId = theShift.workDeclareId
        )
        return dao.getLastShift().executeAsOne().shiftId
    }

    override fun insertDataPerHour(dataPerHourDomain: DataPerHourDomain, workDeclareId: String, shiftId:Long) {
        dao.insertDataPerHour(
            workDeclareId = workDeclareId,
            tip = dataPerHourDomain.extraIncome.toDouble(),
            base = dataPerHourDomain.baseIncome.toDouble(),
            deliveries = dataPerHourDomain.delivers.toDouble(),
            hour = dataPerHourDomain.hour.toString(),
            //if there is no related shift object we will insert 0 that wont connect anywhere...
            shiftId = shiftId!!.toLong()
        )
    }

    override fun insertWorkDeclare(workDeclare: WorkSessionSum) {
       dao.insertWorkDeclare(workDeclareId = workDeclare.startTime.toString(), endDateTime = workDeclare.endTime.toString(),
           baseIncome = workDeclare.baseIncome.toDouble(), deliveries = workDeclare.delivers.toLong(), time = workDeclare.totalTime.toDouble(),
           extraIncome = workDeclare.extraIncome.toDouble(), month = workDeclare.startTime.year.toString()+workDeclare.startTime.month.number, workingPlatform = workDeclare.platform)
    }

    override fun getLastWorkSession():WorkDeclareDomain  {
        val workDeclare = dao.getLastWorkDeclare().executeAsOne()
        val shifts = dao.getShiftsByWorkDeclareId(workDeclareId = workDeclare.workDeclareId).executeAsList()
        val workDeclareDataPerHour = dao.getDataPerHourByWorkDeclareId(workDeclareId = workDeclare.workDeclareId).executeAsList()

        val resultShifts = mutableListOf<ShiftDomain>()

        for (i in shifts){
           val dataPerHour = dao.getDataPerHourByShiftId(i.shiftId).executeAsList()
            resultShifts.add(
                shiftDataToShiftDomain(i,"Wolt", dataPerHourDataToDomain(dataPerHour))
            )
        }

        return WorkDeclareDomain(
            startTime = LocalDateTime.parse(workDeclare.workDeclareId),
            endTime = LocalDateTime.parse(workDeclare.endDateTime),
            time = workDeclare.time.toFloat(),
            deliveries = workDeclare.deliveries.toInt(),
            baseIncome = workDeclare.baseIncome.toFloat(),
            extraIncome = workDeclare.extraIncome.toFloat(),
            yearAndMonth = workDeclare.month,
            dayOfMonth = 7,
            workingPlatform = workDeclare.workingPlatform,
            workPerHour = dataPerHourDataToDomain(workDeclareDataPerHour),
            shifts= resultShifts
        )


    }
}












