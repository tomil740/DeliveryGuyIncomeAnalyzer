package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getLocalDateTimeFromFloatTime
import com.example.deliveryguyincomeanalyzer.domain.model.util.getShiftType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import com.example.deliveryguyincomeanalyzer.domain.useCase.ObjectItemUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.SumDomainData
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getAllTimeSumObj
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

class ObjectItemViewmodel(private val objectItemUseCases: ObjectItemUseCases):ViewModel() {

    /*
    comparedObj :
    an component of the uiState object , will be an workSession sum (for default value only) , this object is just an simple helper value for deffualt initalization
     */
    private var comparedObj: MutableStateFlow<SumObjectInterface> =

        MutableStateFlow(
            SumObj(
                startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5, 17, 30),
                endTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6, 3, 30),
                totalTime = getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ),
                platform = "Wolt",
                baseIncome = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f),
                extraIncome = 300f,
                totalIncome = 6f,
                delivers = 35,
                averageIncomePerDelivery = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) / 35f,
                averageIncomePerHour = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) /
                        getTimeDifferent(
                            startTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 5,
                                17,
                                30
                            ).time,
                            endTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 6,
                                3,
                                30
                            ).time
                        ),
                averageIncomeSubObj = 4f,
                objectType = SumObjectsType.WorkSession,
                shiftType = null,
                objectName = "",
                subObjName = "",
                averageTimeSubObj = 5f,
                sumObjectSourceType = SumObjectSourceType.Archive
            )
        )

    private var valuedObj: MutableStateFlow<SumObjectInterface> =
        MutableStateFlow(
            SumObj(
                startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5, 17, 30),
                endTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6, 3, 30),
                totalTime = getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ),
                platform = "Wolt",
                baseIncome = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f),
                extraIncome = 300f,
                totalIncome = 6f,
                delivers = 35,
                averageIncomePerDelivery = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) / 35f,
                averageIncomePerHour = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) /
                        getTimeDifferent(
                            startTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 5,
                                17,
                                30
                            ).time,
                            endTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 6,
                                3,
                                30
                            ).time
                        ),
                averageIncomeSubObj = 4f,
                objectType = SumObjectsType.WorkSession,
                shiftType = null,
                objectName = "",
                subObjName = "",
                averageTimeSubObj = 7f,
                sumObjectSourceType = SumObjectSourceType.Archive
            )
        )

    private val uiMessage = Channel<String>()

    private val _uiState = MutableStateFlow(
        ObjectItemUiState(
            objectValueSum = comparedObj.value,
            objectComparableSum = comparedObj.value,
            comparableDataMenu = comparedObj.value,
            showComparableMenu = false,
            workingPlatformRemoteMenu = listOf(),
            workingPlatformCustomMenu = listOf(),
            uiMessage = uiMessage
        )
    )

    val uiState = combine(valuedObj, comparedObj, _uiState) { valueObj, comparedObj, _uiState ->
        _uiState.copy(
            objectComparableSum = comparedObj,
            objectValueSum = valueObj,
            uiMessage = uiMessage
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        viewModelScope.launch {
            onEvent(ObjectItemEvents.UpdateRemoteSumObjStatistics)
           // onEvent(ObjectItemEvents.GetValueAllArchive("Any"))

            launch {
                valuedObj.collect {
                    //this will execuate before the combine function of the state , cause geting the old data comparable...

                    delay(10)
               //     onEvent(ObjectItemEvents.GetLocalComparableStatistics(it.platform))
                }
            }


            launch {
                objectItemUseCases.getWorkingPlatformMenu.invoke(false).collect { theLSt ->
                    _uiState.update { it.copy(workingPlatformCustomMenu = theLSt) }
                }
            }
            launch {
                objectItemUseCases.getWorkingPlatformMenu.invoke(true).collect { theLSt ->
                    _uiState.update { it.copy(workingPlatformRemoteMenu = theLSt) }
                }
            }

        }
    }


    fun onEvent(event: ObjectItemEvents) {
        when (event) {
            is ObjectItemEvents.GetMonthSum//StatisticData...
            -> {

            }

            is ObjectItemEvents.InitializeAnObject -> {
                valuedObj.update { (event.theSum) }
            }

            is ObjectItemEvents.GetLocalComparableStatistics -> {

                val theData = when (uiState.value.objectValueSum.objectType) {

                    SumObjectsType.WorkSession -> {
                        objectItemUseCases.getWorkSessionStatisticsData(event.platform)
                    }

                    SumObjectsType.ShiftSession -> {
                        objectItemUseCases.getShiftTypeStatisticsData(
                            event.platform,
                            shiftType = getShiftType(uiState.value.objectValueSum.shiftType!!)
                        )
                    }

                    SumObjectsType.MonthSum -> {
                        val a = objectItemUseCases.getAllTimeMonthData.invoke(event.platform)
                        val monthSums = mutableListOf<WorkSumDomain>()

                        for (i in a) {
                            if (i.data.size > 0)//the minimum set to the platform)
                                monthSums.add(
                                    objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                        i.data
                                    )
                                )
                        }

                        objectItemUseCases.sumDomainData.getAverageDomainObject(monthSums)
                            .toWorkSum(SumObjectSourceType.MyStatistics)
                            .copy(objectName = "MyStatistics")

                    }

                    SumObjectsType.YearSum -> {
                        uiState.value.objectValueSum
                    }


                    SumObjectsType.AllTimeSum -> {
                        val a = objectItemUseCases.getAllTimeMonthData.invoke(event.platform)
                        val monthSums = mutableListOf<WorkSumDomain>()

                        for (i in a) {
                            if (i.data.size > 0)//the minimum set to the platform)
                                monthSums.add(
                                    objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                        i.data
                                    )
                                )
                        }

                        objectItemUseCases.sumDomainData.getSummarizesDomainObject(monthSums)
                            .toWorkSum(SumObjectSourceType.MyStatistics)
                            .copy(objectName = "MyStatistics")
                    }
                }
                if (theData.totalTime == -1f) {

                    onEvent(
                        ObjectItemEvents.SendUiMessage(
                            "There is no match data to present from the local data archive for the ${event.platform} working platform"
                        )
                    )
                } else {
                    comparedObj.update { theData }
                }
            }

            ObjectItemEvents.OnCloseComparableMenu -> {
                _uiState.update { it.copy(showComparableMenu = false) }
            }

            is ObjectItemEvents.OnArchiveComparableMenuPick -> {

                if (event.obj == null) {
                    _uiState.update { it.copy(showComparableMenu = false) }
                } else if (valuedObj.value.objectType == event.obj.objectType) {
                    comparedObj.update { event.obj }
                    _uiState.update { it.copy(showComparableMenu = false) }

                } else {
                    _uiState.update {
                        it.copy(
                            comparableDataMenu = event.obj
                        )
                    }
                }
            }

            ObjectItemEvents.OnOpenComparableMenu -> {
                val a =
                    objectItemUseCases.getAllTimeMonthData.invoke(uiState.value.objectValueSum.platform)

                val b = getAllTimeSumObj(a, uiState.value.objectValueSum.platform)

                _uiState.update {
                    it.copy(
                        comparableDataMenu = b, showComparableMenu = true
                    )
                }
            }

            is ObjectItemEvents.GetValueAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)
                    if (a.isEmpty()) {
                        onEvent(
                            ObjectItemEvents.SendUiMessage(
                                "There is no match data to present from the local data archive for the ${event.workingPlatform} working platform"
                            )
                        )
                    } else {
                        val b = getAllTimeSumObj(a, event.workingPlatform)

                        valuedObj.value = b

                        comparedObj.value = b
                    }
                }
            }

            is ObjectItemEvents.GetComparableMenuAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a, workingPlat = event.workingPlatform)
                    if (b.totalTime == -1f) {
                        //send a snack bar message to the user , there is no match data
                        //to present from the local data archive for $event.workingplatform pick...
                        onEvent(
                            ObjectItemEvents.SendUiMessage(
                                "There is no match data to compare from the local data archive for the ${event.workingPlatform} working platform"
                            )
                        )
                    } else {

                        _uiState.update {
                            it.copy(
                                comparableDataMenu = b
                            )
                        }
                    }
                }
            }

            is ObjectItemEvents.OnValueWorkingPlatformPick -> {
                //need to get the data , check if there is extra data override and update the state
                //  objectItemUseCases.
            }
            /*
            is ObjectItemEvents.OnGeneralStatWorkingPlatformMenu -> {
                val a = objectItemUseCases.getWorkingPlatformMenu.invoke(true)
                _uiState.update { it.copy(workingPlatformRemoteMenu = a) }
            }
            is ObjectItemEvents.OnMyStatWorkingPlatformMenu -> {
                val a = objectItemUseCases.getWorkingPlatformMenu.invoke(false)
                _uiState.update { it.copy(workingPlatformRemoteMenu = a) }
            }

             */
            is ObjectItemEvents.SendUiMessage -> {
                viewModelScope.launch {
                    uiMessage.send(event.mess)
                }
            }

            is ObjectItemEvents.GetGeneralStatisticsComparable -> {

                /*
                if the platform will be any , we will pul all of the platforms average sum object and then make of them an average sumObj
                 */
                var theSumObject:WorkSumDomain? = null
                var allDataPerHourList:List<DataPerHourDomain> = listOf()
                val platformsLst = objectItemUseCases.getUserRemoteWorkingPlatformsList.invoke()
                    .filter { try {objectItemUseCases.getRemoteWorkDeclare(it)
                        true
                    }catch (e:Exception){false}  }
                if(event.platform=="Any"){
                    val sumObjects = mutableListOf<WorkSumDomain>()
                    val listDataPerHour = mutableListOf<List<DataPerHourDomain>>()
                    for (thePlatform in platformsLst){
                        //pull the "frame" the average workDeclare
                        val remoteWorkingPlatform = objectItemUseCases.getRemoteWorkDeclare(thePlatform)
                        //need to extracte frome here the full hours of the declaries , and the  start hour (full hour) 0-60
                        var startTime = remoteWorkingPlatform.startTime.toInt()
                        var fullHours = remoteWorkingPlatform.totalTime
                        if (remoteWorkingPlatform.startTime % 1 != remoteWorkingPlatform.startTime) {
                            startTime += 1
                            fullHours -= 1
                        }
                        if (remoteWorkingPlatform.endTime % 1 != remoteWorkingPlatform.endTime) {
                            fullHours -= 1
                        }
                        val declareDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                            workingPlatformId = thePlatform, size = fullHours.toInt(), startH = startTime
                        )
                        val allDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                            workingPlatformId = thePlatform, size = 24, startH = 0
                        )

                        var theSumObj = objectItemUseCases.getGeneralStatisticsSumObj(
                            remoteWorkDeclare = remoteWorkingPlatform,
                            remoteDataPerHourDomainList = declareDataPerHour
                        )

                        val thePlatformObject = objectItemUseCases.getWorkingPlatformById(thePlatform)

                        val declareShifts = objectItemUseCases.getDeclareShifts(
                            dataPerHourDomain = allDataPerHour.map { it.dataPerHourDomain },
                            liveBuilderState = LiveBuilderState(
                                startTime = theSumObj.startTime,
                                endTime = theSumObj.endTime,
                                totalTime = theSumObj.time,
                                workingPlatform = thePlatform,
                                //all of those are fake data(needed only for the local builder use)
                                1f, 1f, 1f, 1, listOf()
                            ),
                            shiftsFrames = thePlatformObject.shifts
                        )
                        theSumObj = theSumObj.copy(
                            workingPlatform = thePlatformObject.name,
                            shifts = declareShifts.theShifts,
                            baseIncome = (thePlatformObject.baseWage * theSumObj.time)
                        )
                        sumObjects.add(theSumObj)
                        listDataPerHour.add(allDataPerHour.map { it.dataPerHourDomain })
                    }
                    theSumObject = SumDomainData().getAverageDomainObject(a=sumObjects)
                }else {

                    //pull the "frame" the average workDeclare
                    val remoteWorkingPlatform =
                        objectItemUseCases.getRemoteWorkDeclare(event.platform)
                    //need to extracte frome here the full hours of the declaries , and the  start hour (full hour) 0-60
                    var startTime = remoteWorkingPlatform.startTime.toInt()
                    var fullHours = remoteWorkingPlatform.totalTime
                    if (remoteWorkingPlatform.startTime % 1 != remoteWorkingPlatform.startTime) {
                        startTime += 1
                        fullHours -= 1
                    }
                    if (remoteWorkingPlatform.endTime % 1 != remoteWorkingPlatform.endTime) {
                        fullHours -= 1
                    }
                    val declareDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                        workingPlatformId = event.platform,
                        size = fullHours.toInt(),
                        startH = startTime
                    )
                    val allDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                        workingPlatformId = event.platform, size = 24, startH = 0
                    )

                    var theSumObj = objectItemUseCases.getGeneralStatisticsSumObj(
                        remoteWorkDeclare = remoteWorkingPlatform,
                        remoteDataPerHourDomainList = declareDataPerHour
                    )

                    val thePlatform = objectItemUseCases.getWorkingPlatformById(event.platform)

                    val declareShifts = objectItemUseCases.getDeclareShifts(
                        dataPerHourDomain = allDataPerHour.map { it.dataPerHourDomain },
                        liveBuilderState = LiveBuilderState(
                            startTime = theSumObj.startTime,
                            endTime = theSumObj.endTime,
                            totalTime = theSumObj.time,
                            workingPlatform = event.platform,
                            //all of those are fake data(needed only for the local builder use)
                            1f, 1f, 1f, 1, listOf()
                        ),
                        shiftsFrames = thePlatform.shifts
                    )
                    theSumObj = theSumObj.copy(
                        workingPlatform = thePlatform.name,
                        shifts = declareShifts.theShifts,
                        baseIncome = (thePlatform.baseWage * theSumObj.time)
                    )
                    theSumObject=theSumObj
                }

                val theData:SumObjectInterface?= when (uiState.value.objectValueSum.objectType) {

                    SumObjectsType.WorkSession -> {
                        theSumObject.toWorkSum(SumObjectSourceType.GeneralStatistics)
                    }

                    SumObjectsType.ShiftSession -> {
                        if(event.platform == "Any"){
                            var shiftSums = mutableListOf<WorkSumDomain>()
                            for(i in platformsLst){
                                val thePlat = objectItemUseCases.getWorkingPlatformById.invoke(i)
                                val allShifts = objectItemUseCases.getDeclareShifts(
                                    dataPerHourDomain = theSumObject.workPerHour,
                                    liveBuilderState = LiveBuilderState(
                                        startTime = getLocalDateTimeFromFloatTime(0f),
                                        endTime = getLocalDateTimeFromFloatTime((23 + (59f / 60f))),
                                        totalTime = (23 + (59f / 60f)),
                                        workingPlatform = i,
                                        //all of those are fake data(needed only for the local builder use)
                                        1f, 1f, 1f, 1, listOf()
                                    ),
                                    shiftsFrames = thePlat.shifts
                                )
                                val shiftType = getShiftType(uiState.value.objectValueSum.shiftType!!)
                                var result:ShiftDomain = allShifts.theShifts.first()
                                for (i in allShifts.theShifts) {
                                    if (getShiftType(i.shiftType) == shiftType)
                                        result = i
                                }
                                shiftSums.add(result.toWorkSumDomain())

                            }
                            SumDomainData().getAverageDomainObject(shiftSums).toWorkSum(SumObjectSourceType.GeneralStatistics)
                        }else {
                            val allDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                                workingPlatformId = event.platform, size = 24, startH = 0
                            ).map { it.dataPerHourDomain }
                            val workingPlatformObject =
                                objectItemUseCases.getWorkingPlatformById.invoke(event.platform)
                            val allShifts = objectItemUseCases.getDeclareShifts(
                                dataPerHourDomain =allDataPerHour,
                                liveBuilderState = LiveBuilderState(
                                    startTime = getLocalDateTimeFromFloatTime(0f),
                                    endTime = getLocalDateTimeFromFloatTime((23 + (59f / 60f))),
                                    totalTime = (23 + (59f / 60f)),
                                    workingPlatform = event.platform,
                                    //all of those are fake data(needed only for the local builder use)
                                    1f, 1f, 1f, 1, listOf()
                                ),
                                shiftsFrames = workingPlatformObject.shifts
                            )
                            val shiftType = getShiftType(uiState.value.objectValueSum.shiftType!!)
                            var result: SumObjectInterface =
                                allShifts.theShifts.first().toShiftSum()
                            for (i in allShifts.theShifts) {
                                if (getShiftType(i.shiftType) == shiftType)
                                    result = i.toShiftSum(SumObjectSourceType.GeneralStatistics)
                            }
                            result
                        }
                    }

                    SumObjectsType.MonthSum -> {
                        if(event.platform=="Any"){
                            var monthSum = mutableListOf<WorkSumDomain>()
                            for(i in platformsLst){
                                val a =
                                objectItemUseCases.getTopLevelGeneralStatisticsSumObj.invoke(
                                    workPlatform = i,
                                    basicWorkSum = theSumObject
                                )
                                monthSum.add(a)
                            }
                            SumDomainData().getAverageDomainObject(monthSum).toWorkSum(SumObjectSourceType.GeneralStatistics)
                        }else{
                        //start by pull the average per month workDeclaries
                        objectItemUseCases.getTopLevelGeneralStatisticsSumObj.invoke(
                            workPlatform = event.platform,
                            basicWorkSum = theSumObject
                        ).toWorkSum(SumObjectSourceType.GeneralStatistics)
                        }
                    }

                    SumObjectsType.YearSum -> TODO()

                    SumObjectsType.AllTimeSum -> {
                        onEvent(ObjectItemEvents.SendUiMessage("There is no match data option on General Statistics to compare with All Time sum "))
                        null
                    }
                }

                if (theData!=null) {
                    comparedObj.update { theData }
                }

            }

            ObjectItemEvents.UpdateRemoteSumObjStatistics -> {
                viewModelScope.launch {
                    // CoroutineScope(Dispatchers.IO).launch {
                    val objectSToUpdate = objectItemUseCases.shouldUpdateRemoteStatistics.invoke()
                    if (objectSToUpdate.isNotEmpty()) {
                        //calculation and update of the remoteSumObjects
                        val userPlatformList =
                            objectItemUseCases.getUserRemoteWorkingPlatformsList.invoke()
                        for (workingPlatform in userPlatformList) {
                            var workSessionsAmount: Int = 0
                            for (theMonth in objectSToUpdate) {
                                workSessionsAmount +=
                                    objectItemUseCases.getMonthWorkDeclareAmount.invoke(
                                        workingPlatform = workingPlatform, monthId = theMonth
                                    )
                            }
                            //update the table object with the new data
                            objectItemUseCases.updateRemoteSumObj(
                                workingPlatform = workingPlatform,
                                newAmount = workSessionsAmount,
                                monthAmount = objectSToUpdate.size
                            )
                        }
                        objectItemUseCases.updateUserData.invoke()
                    }
                }
            }
        }
    }
}

















