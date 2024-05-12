package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectIntType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.isShiftSession
import com.example.deliveryguyincomeanalyzer.domain.model.util.getLocalDateTimeFromFloatTime
import com.example.deliveryguyincomeanalyzer.domain.useCase.ObjectItemUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.SumDomainData
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getAllTimeSumObj
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util.DefaultData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ObjectItemViewmodel(private val objectItemUseCases: ObjectItemUseCases):ViewModel() {

    private val defaultData = DefaultData()

    /*
    comparedObj :
    an component of the uiState object , will be an workSession sum (for default value only) , this object is just an simple helper value for deffualt initalization
     */
    private var comparedObj: MutableStateFlow<SumObjectInterface> = defaultData.comparedObj

    private var valuedObj: MutableStateFlow<SumObjectInterface> = defaultData.valuedObj

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
            onEvent(ObjectItemEvents.GetValueAllArchive("Any"))
            CoroutineScope(Dispatchers.IO).launch {
                    valuedObj.collect {
                        //this will execuate before the combine function of the state , cause geting the old data comparable...
                        delay(100)
                    //    onEvent(ObjectItemEvents.GetComparableUserStatistics(it.platform))
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
            is ObjectItemEvents.InitializeAnObject -> {
                valuedObj.update { (event.theSum) }
            }
            ObjectItemEvents.OnCloseComparableMenu -> {
                _uiState.update { it.copy(showComparableMenu = false) }
            }
            is ObjectItemEvents.SendUiMessage -> {
                viewModelScope.launch {
                    uiMessage.send(event.mess)
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
            /*
            Comparable object , events list of the picker functions...
            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
             */
            //MyStatistics:
            is ObjectItemEvents.GetComparableUserStatistics -> {
                val theData = getUserStatisticsByType(theType = uiState.value.objectValueSum.objectType.name, platform = event.platform)
                if (theData?.totalTime == -1f || theData==null) {

                    onEvent(
                        ObjectItemEvents.SendUiMessage(
                            "There is no match data to present from the local data archive for the ${event.platform} working platform"
                        )
                    )
                } else {
                    comparedObj.update { theData }
                }
            }
            is ObjectItemEvents.GetComparableGeneralStatistics -> {
                val theData = getGeneralStatisticsByType(platform = event.platform,uiState.value.objectValueSum.objectType.name)
                if (theData!=null) {
                    comparedObj.update { theData }
                }
            }
            //on specific Archive pick
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
            //this will be on restart of all of the archive data , in practice :
            //pull all the available match data
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
            /*
            Value Object functions... ,All most the same as the comparable for the data with the match changes ...
            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
             */
            //this for initalize the archive , according to any platform that will be picked (sort the list)
            //on the main object we can navigate throw the archive and the initalize event...
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
                    }
                }
            }
            //for the value by default we will pull the bigest statistic (month) and with its match option menu the use could cahnge it...
            is ObjectItemEvents.GetValueGeneralStatistics -> {
                //will send an string that could be other one of them ...
                val theData = try {
                    val type = isShiftSession(event.uiData)
                    type
                    //if we send a type by this function , that will be from the type menu and we want to
                    // update to the matched current object matched picked type
                    getGeneralStatisticsByType(theType = event.uiData, platform = uiState.value.objectValueSum.platform)
                }catch (e:Exception) {
                    //if we initalize new platform , we want to show as default the maximum amount of data
                   getGeneralStatisticsByType(platform = event.uiData, theType = SumObjectsType.MonthSum.name)
                }
                if(theData!=null)
                    valuedObj.value = theData
                    else{
                    onEvent(ObjectItemEvents.SendUiMessage("Error Appeared on the general statistics loading process of the ${event.uiData} working platform "))
                }
            }
            is ObjectItemEvents.GetValueLocalStatistics -> {
                //will send an string that could be other one of them ...
                val theData = try {
                    val type = isShiftSession(event.uiData)
                    //if the type will not fail...
                    type
                    getUserStatisticsByType(theType = event.uiData, platform = uiState.value.objectValueSum.platform)
                }catch (e:Exception) {
                    getUserStatisticsByType(platform = event.uiData, theType = SumObjectsType.MonthSum.name)
                }
                if(theData.totalTime > 0)
                    valuedObj.value = theData
                else{
                    onEvent(ObjectItemEvents.SendUiMessage("Error Appeared on the User statistics loading process of the ${event.uiData} working platform "))
                }
            }
            //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
            //this for the system to apply when need to update the remote server data according to long time change...
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

    private fun getUserStatisticsByType(theType : String, platform:String):SumObjectInterface{
        //check with matched function if the type is one of the Shift types which is sub type of shiftSession...
        val objectType = isShiftSession(theType)
        return when (objectType) {

            SumObjectsType.WorkSession -> {
                objectItemUseCases.getWorkSessionStatisticsData(platform)
            }

           SumObjectsType.ShiftSession-> {
               //if that is an shift Session we will get as the parameter the shift type , and by the function
               //we will get to the matched type...

                   objectItemUseCases.getShiftStatisticsDataByTypeAndWp(
                       platform,
                       shiftType = getSumObjectIntType(theType)
                   )

               }


            SumObjectsType.MonthSum -> {
                val a = objectItemUseCases.getAllTimeMonthData.invoke(platform)
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
                val a = objectItemUseCases.getAllTimeMonthData.invoke(platform)
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
    }

    private fun getGeneralStatisticsByType(platform:String, theType:String) : SumObjectInterface? {
        val objectType = isShiftSession(theType)
        /*
                if the platform will be any , we will pul all of the platforms average sum object and then make of them an average sumObj
                 */
        var theSumObject:WorkSumDomain? = null
        /*
        In general to this feature we should have all the needed data on the remote server which means all
        we need to produce at this level is just to pull All of the remote working platforms data...
        *for now* we will implement only for those that has an remoteSumObjects intilazitaion objects
        which is not neccecry in the prudection app ...
         */
        //at prudection will be some "getRemoteWorkingPlatform menu..."
        val platformsLst = listOf("Dominos-Center","Dominos-North","Dominos-South","Wolt-Center","Wolt-North")

        if(platform=="Any"){
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
                objectItemUseCases.getRemoteWorkDeclare(platform)
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
                workingPlatformId = platform,
                size = fullHours.toInt(),
                startH = startTime
            )
            val allDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                workingPlatformId = platform, size = 24, startH = 0
            )

            var theSumObj = objectItemUseCases.getGeneralStatisticsSumObj(
                remoteWorkDeclare = remoteWorkingPlatform,
                remoteDataPerHourDomainList = declareDataPerHour
            )

            val thePlatform = objectItemUseCases.getWorkingPlatformById(platform)

            val declareShifts = objectItemUseCases.getDeclareShifts(
                dataPerHourDomain = allDataPerHour.map { it.dataPerHourDomain },
                liveBuilderState = LiveBuilderState(
                    startTime = theSumObj.startTime,
                    endTime = theSumObj.endTime,
                    totalTime = theSumObj.time,
                    workingPlatform = platform,
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

        return when (objectType) {

            SumObjectsType.WorkSession -> {
                theSumObject.toWorkSum(SumObjectSourceType.GeneralStatistics)
            }

            SumObjectsType.ShiftSession-> {
                if(platform == "Any"){
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
                        val shiftType = getSumObjectIntType(uiState.value.objectValueSum.shiftType!!)
                        var result:ShiftDomain = allShifts.theShifts.first()
                        for (i in allShifts.theShifts) {
                            if (getSumObjectIntType(i.shiftType) == shiftType)
                                result = i
                        }
                        shiftSums.add(result.toWorkSumDomain())

                    }
                    SumDomainData().getAverageDomainObject(shiftSums).toWorkSum(SumObjectSourceType.GeneralStatistics)
                }else {
                    val allDataPerHour = objectItemUseCases.getRemoteDataPerHour(
                        workingPlatformId =platform, size = 24, startH = 0
                    ).map { it.dataPerHourDomain }
                    val workingPlatformObject =
                        objectItemUseCases.getWorkingPlatformById.invoke(platform)
                    val allShifts = objectItemUseCases.getDeclareShifts(
                        dataPerHourDomain =allDataPerHour,
                        liveBuilderState = LiveBuilderState(
                            startTime = getLocalDateTimeFromFloatTime(0f),
                            endTime = getLocalDateTimeFromFloatTime((23 + (59f / 60f))),
                            totalTime = (23 + (59f / 60f)),
                            workingPlatform = platform,
                            //all of those are fake data(needed only for the local builder use)
                            1f, 1f, 1f, 1, listOf()
                        ),
                        shiftsFrames = workingPlatformObject.shifts
                    )
                    /*
                    at this part we will use the passed shift type, the original one before our special maping...
                     */
                    val shiftType = getSumObjectIntType(theType)
                    var result: SumObjectInterface? =
                        allShifts.theShifts.first().toShiftSum()
                    for (i in allShifts.theShifts) {
                        if (getSumObjectIntType(i.shiftType) == shiftType)
                            result = i.toShiftSum(SumObjectSourceType.GeneralStatistics)
                    }
                    if(result?.sumObjectSourceType!=SumObjectSourceType.GeneralStatistics) {
                        //onEvent(ObjectItemEvents.SendUiMessage("There is no matched shift type data ..."))
                        result = null
                    }
                    result
                }
            }

            SumObjectsType.MonthSum -> {
                if(platform=="Any"){
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
                        workPlatform = platform,
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
    }
}

















