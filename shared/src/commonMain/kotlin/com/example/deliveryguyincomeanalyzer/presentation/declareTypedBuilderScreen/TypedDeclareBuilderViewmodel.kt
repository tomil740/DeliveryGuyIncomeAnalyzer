package com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.TypeBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getPlatformIdComponents
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import com.example.deliveryguyincomeanalyzer.domain.useCase.screensCollectionUseCases.TypedDeclareBuilderUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getAllTimeSumObj
import com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen.util.BuilderValuesStateNotes
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util.DefaultData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class TypedDeclareBuilderViewmodel(val typedDeclareBuilderUseCases: TypedDeclareBuilderUseCases):ViewModel() {
    /*
   comparedObj :
   an component of the uiState object , will be an workSession sum (at this screen) , we will get the average of them from our db archive ...
    */
    private val builderValuesStateNotes = MutableStateFlow(BuilderValuesStateNotes(false,false,false))
    private val comparedObj: SumObjectInterface = DefaultData().comparedObj.value

    private val uiMessage = Channel<String>()

    private var typeBuilderState = MutableStateFlow(TypeBuilderState(
        startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        totalTime =  getTimeDifferent(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time),
        workingPlatform = "Wolt-Center", baseWage = 35f, extras = 55f, delivers = 5
    )
    )

    private var _uiState = MutableStateFlow(TypedDeclareBuilderUiState(
        totalIncome = (typeBuilderState.value.totalTime*typeBuilderState.value.baseWage)+typeBuilderState.value.extras,
        currentSum = typeBuilderState.value.toWorkSessionSum(),
        comparableObj = comparedObj,//just an fake workSesionSum , should be the average one
        workingPlatformRemoteMenu = listOf(),
        workingPlatformCustomMenu = listOf(),
        comparableMenuData = comparedObj,
        showComparableMenu = false,
        uiMessage = uiMessage,
        typeBuilderState = typeBuilderState.value,
        errorMes = "Clear"
    ))

    val state : StateFlow<TypedDeclareBuilderUiState> =
        combine(typeBuilderState, _uiState) {
                typeBuilderState, state ->
            //update the builder state accordingly with the data of it as well
            val c = typeBuilderState.toWorkSessionSum()
            TypedDeclareBuilderUiState(
                typeBuilderState=typeBuilderState,
                currentSum = c,
                totalIncome = c.totalIncome,
                comparableObj = state.comparableObj,
                workingPlatformCustomMenu = state.workingPlatformCustomMenu,
                workingPlatformRemoteMenu = state.workingPlatformRemoteMenu,
                comparableMenuData = state.comparableObj,
                showComparableMenu = state.showComparableMenu,
                uiMessage = uiMessage,
                errorMes = state.errorMes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        viewModelScope.launch {
            launch {
                typedDeclareBuilderUseCases.getWorkingPlatformMenu.invoke(false).collect { theLSt ->
                    _uiState.update { it.copy(workingPlatformCustomMenu = theLSt) }
                }
            }

            launch {
                builderValuesStateNotes.collect{
                    var theMes = ""
                    if (!it.delivers)
                        theMes+="There is no delivers ?"
                    if (!it.extra)
                        theMes+="There is no tips ?"
                    if (!it.totalTime)
                        theMes+="This is unusual work Session time length"
                    if(theMes == "")
                        theMes = "Nice work session!"

                    _uiState.update { it.copy(errorMes = theMes) }
                }
            }

            launch {
                typedDeclareBuilderUseCases.getWorkingPlatformMenu.invoke(true).collect { theLSt ->
                    _uiState.update { it.copy(workingPlatformRemoteMenu = theLSt) }
                }
            }
        }

    }

    fun onTypedDeclareBuilderEvent(event:TypedDeclareBuilderEvents){
        when(event){
            is TypedDeclareBuilderEvents.GetComparableMenuAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = typedDeclareBuilderUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a,workingPlat = event.workingPlatform)
                    if (b.totalTime == -1f) {
                        //send a snack bar message to the user , there is no match data
                        //to present from the local data archive for $event.workingplatform pick...
                        onTypedDeclareBuilderEvent(
                            TypedDeclareBuilderEvents.SendUiMessage(
                                "There is no match data to compare from the local data archive for the ${event.workingPlatform} working platform"))
                    }else {

                        _uiState.update {
                            it.copy(
                                comparableObj = b
                            )
                        }
                    }
                }
            }
            is TypedDeclareBuilderEvents.GetComparableStatistics -> {
                val theData = typedDeclareBuilderUseCases.getWorkSessionStatisticsData(event.platform)


                if(theData.totalTime==-1f){

                    onTypedDeclareBuilderEvent(
                        TypedDeclareBuilderEvents.SendUiMessage(
                            "There is no match data to present from the local data archive for the ${event.platform} working platform"))
                }else {
                    _uiState.update {
                        it.copy(
                            comparableObj = theData
                        )
                    }
                }
            }
            is TypedDeclareBuilderEvents.OnArchiveComparableMenuPick -> {
                if(event.obj==null){
                    _uiState.update { it.copy(showComparableMenu = false) }
                }
                else if (event.obj.objectType == SumObjectsType.WorkSession){
                    _uiState.update { it.copy(comparableObj = event.obj,showComparableMenu = false) }

                }else{
                    _uiState.update { it.copy(
                        comparableObj = event.obj
                    ) }
                }
            }
            TypedDeclareBuilderEvents.OnCloseComparableMenu -> {
                _uiState.update { it.copy(showComparableMenu = false) }
            }
            is TypedDeclareBuilderEvents.OnComparableWorkingPlatformPick -> {
                /*
                I am not shure why I need this use case at all
                on comparable wp pick we can use the getAll...
                GetComparableMenuAllArchive
                 */
               // comparedObj. { typedDeclareBuilderUseCases.getWorkingPlatformById(event.workingPlatId)}
            }
            //for some resaon we allwase starte from zero (which is illegal to the delivers amount to be at...)
            is TypedDeclareBuilderEvents.OnDelivers -> {
                    val a= event.deliversVal.toInt()
                println("this is delivers $a")
                    typeBuilderState.update { it.copy(delivers = a) }
                if(a!=1) {
                    builderValuesStateNotes.update { it.copy(delivers = true) }
                }else{
                    builderValuesStateNotes.update { it.copy(delivers = false) }
                }
            }
            is TypedDeclareBuilderEvents.OnEndTime -> {
                val a = LocalTime.parse(event.eTime)
                var theResult=LocalDateTime(date = typeBuilderState.value.startTime.date, time = a)
                if(typeBuilderState.value.startTime.time > a)
                    theResult=LocalDateTime(date = theResult.date.plus(DatePeriod(days = 1)), time = a)
                val totalTime = try { getTimeDifferent(endTime = theResult.time, startTime = typeBuilderState.value.startTime.time)
                }catch (e:Exception){ -1f}
                if(totalTime > 23 || totalTime == -1f){
                    onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.
                    SendUiMessage("The declared time is illegal, the maximum length of an workSession is 23 hours..."))
                }else{
                    typeBuilderState.update { it.copy(endTime = theResult, totalTime = totalTime) }
                    if(totalTime < 4 || totalTime > 14) {
                        builderValuesStateNotes.update { it.copy(totalTime = false) }
                    }else{
                        builderValuesStateNotes.update { it.copy(totalTime = true) }
                    }
                }


            }
            is TypedDeclareBuilderEvents.OnExtra -> {
                    val a = event.extraVal.toFloat()
                    typeBuilderState.update { it.copy(extras =a) }
                if(a !=0f) {
                    builderValuesStateNotes.update { it.copy(extra = true) }
                }else {
                    builderValuesStateNotes.update { it.copy(extra = false) }
                }

            }

            is TypedDeclareBuilderEvents.OnMainPlatformPick -> {
                /*we can pull its data as an flow collect it in some coroutine , that will be
                cancled on every chnage to the new one ,
                by that we will calculate an update all of the neccecry state attributes on change
                and can delete the wokring platform state flow ...
                 */
                viewModelScope.launch {
                       val newWp = typedDeclareBuilderUseCases.getWorkingPlatformById(event.platform)
                        typeBuilderState.update {
                            it.copy(
                                baseWage = newWp.baseWage,
                                totalBase = newWp.baseWage*it.totalTime,
                                workingPlatform = newWp.name
                            )
                        }
                }
            }

            TypedDeclareBuilderEvents.OnOpenComparableMenu -> {
                val a = typedDeclareBuilderUseCases.getAllTimeMonthData.invoke(typeBuilderState.value.workingPlatform)

                val b = getAllTimeSumObj(a,_uiState.value.comparableObj.platform)

                _uiState.update { it.copy(
                    comparableMenuData = b, showComparableMenu = true
                ) }
            }

            is TypedDeclareBuilderEvents.OnStartTime -> {

                val a = LocalTime.parse(event.sTime)
                var theResult=LocalDateTime(date = typeBuilderState.value.startTime.date, time = a)
                val totalTime = try { getTimeDifferent(startTime = theResult.time, endTime = typeBuilderState.value.endTime.time)
                }catch (e:Exception){ -1f}
                if(totalTime > 23 || totalTime == -1f){
                    onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.
                    SendUiMessage("The declared time is illegal, the maximum length of an workSession is 23 hours..."))
                }else{
                    typeBuilderState.update { it.copy(startTime = theResult, totalTime = totalTime) }
                    if(totalTime < 4 || totalTime > 14) {
                        builderValuesStateNotes.update { it.copy(totalTime = false) }
                    }else{
                        builderValuesStateNotes.update { it.copy(totalTime = true) }
                    }
                }
            }


            TypedDeclareBuilderEvents.OnSubmitDeclare -> {

                if(typeBuilderState.value.totalTime < 2f){
                    onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.SendUiMessage
                        ("Fail to insert ,your work declare must be in minimum of 2 hours long in order to be insert"))
                }else {

                    viewModelScope.launch {
                        //we will get all of the working platform data
                        //get working platform data
                        val workingPlatformObj: WorkingPlatform =
                            typedDeclareBuilderUseCases.getWorkingPlatformById(typeBuilderState.value.workingPlatform)

                        val dataPerHour: List<DataPerHourDomain> =
                            typedDeclareBuilderUseCases.getTypedDeclareDataPerHour(state.value.typeBuilderState) //getLiveDeclareDataPerHour(typedBuilderState) , will calculate the matched hours data according to the sumarise data ...
                        /*
                    should update the data per hour remote objects as well...
                     */
                        val currentRemoteDataPerHour =
                            typedDeclareBuilderUseCases.getRemoteDataPerHour.invoke(
                                size = dataPerHour.size,
                                startH = dataPerHour.first().hour,
                                workingPlatformId = typeBuilderState.value.workingPlatform
                            )
                        val workingPlatformIdComponents =
                            getPlatformIdComponents(workingPlatformObj.name)
                        typedDeclareBuilderUseCases.updateRemoteDataPerHour.invoke(
                            workingPlatform = workingPlatformIdComponents.platformName,
                            workingZone = workingPlatformIdComponents.workingZone,
                            currentObj = currentRemoteDataPerHour,
                            theObj = dataPerHour
                        )

                        val declareShifts =
                            //should be adjust the liveBuilder shift use case , to work with the typed as well
                            typedDeclareBuilderUseCases.getDeclareShifts(
                                dataPerHour,
                                liveBuilderState = typeBuilderState.value.toLiveBuilderState(),
                                workingPlatformObj.shifts
                            )

                        typedDeclareBuilderUseCases.insertWorkDeclare(typeBuilderState.value.toWorkSessionSum())

                        //calculate and insert the new remoteWorkDeclare object according to this data
                        val currentObj = typedDeclareBuilderUseCases.getRemoteWorkDeclare.invoke(
                            workingPlatformObj.name
                        )
                        //*get the current object*
                        //*check if there is the minimum length for statistics remoteWorkDeclare , minimum half of the current one*
                        if (typeBuilderState.value.totalTime >= (currentObj.totalTime / 2f)) {
                            //*Call the "big" use case UpdateRemoteWorkDeclare*
                            typedDeclareBuilderUseCases.updateRemoteWorkDeclare.invoke(
                                currentObj,
                                typeBuilderState.value.startTime,
                                typeBuilderState.value.endTime
                            )
                        }
                        val workDeclareId = typeBuilderState.value.startTime.toString()
                        for (i in declareShifts.theShifts) {
                            val shiftId =
                                typedDeclareBuilderUseCases.insertShiftObj(i, workDeclareId)
                            for (j in i.dataPerHour) {
                                typedDeclareBuilderUseCases.insertDataPerHour(
                                    dataPerHourDomain = j,
                                    workDeclareId,
                                    shiftId
                                )
                            }
                        }
                        for (i in declareShifts.unUsedData) {
                            //this will be the unshift related dataperHour objects that will be insert ...
                            typedDeclareBuilderUseCases.insertDataPerHour(
                                dataPerHourDomain = i,
                                workDeclareId,
                                0
                            )
                        }
                    }
                }
            }
            is TypedDeclareBuilderEvents.SendUiMessage -> {
                viewModelScope.launch {
                    uiMessage.send(event.mess)
                }
            }

            is TypedDeclareBuilderEvents.OnDate -> {

                val a = LocalDate.parse(event.date)
                typeBuilderState.update { it.copy( startTime = LocalDateTime(date = a, time = it.startTime.time)) }
                onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnEndTime(typeBuilderState.value.endTime.time.toString()))
            }
        }
    }














}






























