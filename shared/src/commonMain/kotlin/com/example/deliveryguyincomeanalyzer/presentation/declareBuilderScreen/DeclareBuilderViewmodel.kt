package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import com.example.deliveryguyincomeanalyzer.domain.useCase.DeclareBuilderUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getAllTimeSumObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getDeliversData
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util.DefaultData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DeclareBuilderViewmodel(private val declareBuilderUseCases: DeclareBuilderUseCases): ViewModel() {
    /*
    comparedObj :
    an component of the uiState object , will be an workSession sum (at this screen) , we will get the average of them from our db archive ...
     */
    private val comparedObj: SumObjectInterface = DefaultData().comparedObj.value

    private val uiMessage = Channel<String>()

    /*
    deliversItems :
    an component of the uiState object represent the current deliveries item list , will be update from the ui and
    the data will be calculate to the object below
     */
    private var deliversItems = MutableStateFlow<List<LiveDeliveryItem>>(listOf())

    /*
   typeBuilderState :
   an component of the uiState object represent in general teh list of deliversItem above plus some extra data
   this will be update according the list above
    */
    private var liveBuilderState = MutableStateFlow(LiveBuilderState(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        totalTime =  getTimeDifferent(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time),
        workingPlatform = "Wolt", baseWage = 35f, extras = 55f, delivers = 5,
        deliversItem = deliversItems.value
    ))

    private var _uiState = MutableStateFlow(DeclareBuilderUiState
        (liveBuilderState=liveBuilderState.value,
        totalIncome = (liveBuilderState.value.totalTime*liveBuilderState.value.baseWage)+liveBuilderState.value.extras,
        currentSum = liveBuilderState.value.toWorkSessionSum(),
        comparableObj = comparedObj,//just an fake workSesionSum , should be the average one
        workingPlatformRemoteMenu = listOf(),
        workingPlatformCustomMenu = listOf(),
        comparableMenuData = comparedObj,
        showComparableMenu = false,
        uiMessage = uiMessage
        )
    )
    init {
        // onDeclareBuilderEvent(DeclareBuilderEvents.OnComparableWorkingPlatformPick("Wolt-Center"))
        //this method will initalize our builderState ...
        onDeclareBuilderEvent(DeclareBuilderEvents.GetLiveBuilderState)

        viewModelScope.launch {
            launch {
                declareBuilderUseCases.getWorkingPlatformMenu.invoke(false).collect { theLSt->
                    _uiState.update {it.copy(workingPlatformCustomMenu = theLSt)  }
                }
            }
            launch {
                declareBuilderUseCases.getWorkingPlatformMenu.invoke(true).collect { theLSt->
                    _uiState.update {it.copy(workingPlatformRemoteMenu = theLSt)}
                }
            }
        }

            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    liveBuilderState.update {
                        it.copy(
                            endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        )
                    }
                    delay(360000 )
                }
            }
    }

    val state : StateFlow<DeclareBuilderUiState> =
        combine(deliversItems,liveBuilderState, _uiState) {
                deliversLst,liveBuilderState, state ->
            //calculate the list sumarise data
            val a = getDeliversData(deliversLst)
            //update the builder state accordingly with the data of it as well
            val b =LiveBuilderState(startTime = liveBuilderState.startTime, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                totalTime =  getTimeDifferent(startTime = liveBuilderState.startTime.time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time),
                workingPlatform = liveBuilderState.workingPlatform, baseWage = liveBuilderState.baseWage, extras =a.extras, delivers = a.delivers, deliversItem =a.deliversItem)
            val c = b.toWorkSessionSum()
            DeclareBuilderUiState(
                liveBuilderState=b,
                currentSum = c,
                totalIncome = c.totalIncome,
                comparableObj = state.comparableObj,
                workingPlatformCustomMenu = state.workingPlatformCustomMenu,
                workingPlatformRemoteMenu = state.workingPlatformRemoteMenu,
                comparableMenuData = state.comparableObj,
                showComparableMenu = state.showComparableMenu,
                uiMessage = uiMessage
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun onDeclareBuilderEvent(event:DeclareBuilderEvents) {
        when (event) {
            is DeclareBuilderEvents.OnAddDeliveryItem -> {
                val a = mutableListOf<LiveDeliveryItem>()
                deliversItems.update {
                    for (i in it) {
                        a.add(i)
                    }
                    a.add(
                        LiveDeliveryItem(
                            time = Clock.System.now()
                                .toLocalDateTime(TimeZone.currentSystemDefault()),
                            extra = event.extra
                        )
                    )
                    a
                }
            }

            is DeclareBuilderEvents.OnDeleteDeliveryItem -> {
                val a = mutableListOf<LiveDeliveryItem>()
                deliversItems.update {
                    var counter = 0
                    for (i in it) {
                        if (counter != event.id)
                            a.add(i)
                        counter += 1
                    }
                    a
                }
            }


            DeclareBuilderEvents.OnSubmitDeclare ->{
                val a = LocalDate(2024,Month.APRIL,15)
                val b = LocalDate(2024,Month.APRIL,16)
                val theLst= listOf(LiveDeliveryItem(time = LocalDateTime(date = a,time=LocalTime(14,45)), extra = 2f),
                    LiveDeliveryItem(time = LocalDateTime(date = a,time=LocalTime(15,15)), extra = 29f),
                    LiveDeliveryItem(time = LocalDateTime(date = a,time=LocalTime(15,45)), extra = 21f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(16,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(16,55)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(17,45)), extra = 2f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(18,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(18,55)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(18,45)), extra = 2f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(19,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(19,55)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(20,45)), extra = 2f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(21,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(21,56)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(22,45)), extra = 49f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(22,57)), extra = 92f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(23,15)), extra = 92f),
                    LiveDeliveryItem(time = LocalDateTime(date = a,time= LocalTime(23,25)), extra = 84f),
                    LiveDeliveryItem(time = LocalDateTime(a,LocalTime(23,45)), extra = 23f),
                    LiveDeliveryItem(time = LocalDateTime(b,LocalTime(0,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = b,time= LocalTime(0,55)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(b,LocalTime(1,45)), extra = 2f),
                    LiveDeliveryItem(time = LocalDateTime(b,LocalTime(2,45)), extra = 2f),LiveDeliveryItem(time = LocalDateTime(date = b,time= LocalTime(2,55)), extra = 4f),
                    LiveDeliveryItem(time = LocalDateTime(b,LocalTime(3,45)), extra = 2f),
                )

                val fakeWorkingPlatform =  "Dominos-Center"

                var extras = 0f
                for (i in theLst){
                    extras+=i.extra
                }
                val shifts = declareBuilderUseCases.getWorkingPlatformById(fakeWorkingPlatform)

                    val fakeLiveBuilderState = LiveBuilderState(
                    startTime = LocalDateTime(date = a, time = LocalTime(14,30)), endTime = LocalDateTime(b,LocalTime(2,30)), totalTime = getTimeDifferent(startTime = LocalDateTime(date = a, time = LocalTime(16,30)).time, endTime = LocalDateTime(b,LocalTime(4,4)).time),
                    workingPlatform = fakeWorkingPlatform,
                    baseWage = shifts.baseWage,
                    extras = extras, delivers = theLst.size,
                    deliversItem = theLst)
                viewModelScope.launch {
                    //we will get all of the working platform data
                    //get working platform data
                    val dataPerHour = declareBuilderUseCases.getLiveDeclareDataPerHour(fakeLiveBuilderState,shifts.baseWage)

                    val currentRemoteDataPerHour = declareBuilderUseCases.getRemoteDataPerHour.invoke(size = dataPerHour.size, startH = dataPerHour.first().hour, workingPlatformId = fakeWorkingPlatform)

                    declareBuilderUseCases.updateRemoteDataPerHour.invoke("Dominos","Center",currentRemoteDataPerHour,dataPerHour)

                    val declareShifts = declareBuilderUseCases.getDeclareShifts(
                        dataPerHour, fakeLiveBuilderState,shifts.shifts
                    )
                    declareBuilderUseCases.insertWorkDeclare(fakeLiveBuilderState.toWorkSessionSum())
                    //calculate and insert the new remoteWorkDeclare object according to this data
                   val currentObj =  declareBuilderUseCases.getRemoteWorkDeclare.invoke(fakeWorkingPlatform)
                    //*get the current object*
                    //*check if there is the minimum length for statistics remoteWorkDeclare , minimum half of the current one*
                    if(fakeLiveBuilderState.totalTime >= (currentObj.totalTime/2f)){
                        //*Call the "big" use case UpdateRemoteWorkDeclare*
                        declareBuilderUseCases.updateRemoteWorkDeclare.invoke(currentObj,fakeLiveBuilderState.startTime,fakeLiveBuilderState.endTime)
                    }
                    val workDeclareId =fakeLiveBuilderState.startTime.toString()
                    for (i in declareShifts.theShifts){
                        val shiftId = declareBuilderUseCases.insertShiftObj(i,workDeclareId)
                        for (j in i.dataPerHour){
                            declareBuilderUseCases.insertDataPerHour(dataPerHourDomain = j,workDeclareId,shiftId)
                        }
                    }
                    for (i in declareShifts.unUsedData){
                        //this will be the unshift related dataperHour objects that will be insert ...
                        declareBuilderUseCases.insertDataPerHour(dataPerHourDomain = i,workDeclareId,0)
                    }
                }
            }
            is DeclareBuilderEvents.OnMainPlatformPick -> {
                viewModelScope.launch {
                    val newWp = declareBuilderUseCases.getWorkingPlatformById(event.platform)
                    liveBuilderState.update {
                        it.copy(
                            baseWage = newWp.baseWage,
                            totalBase = newWp.baseWage*it.totalTime,
                            workingPlatform = newWp.name
                        )
                    }
                } }

            DeclareBuilderEvents.GetLiveBuilderState -> {
                viewModelScope.launch {
                    //get the current builder state to be setup
                    val a = declareBuilderUseCases.getLiveDeliveryState.invoke()
                    //to update the all around data
                    liveBuilderState.update {a}
                    //for updating the exact delivries sum data
                    deliversItems.update { a.deliversItem }
                }
            }

            is DeclareBuilderEvents.SaveLiveBuilderState -> {
                viewModelScope.launch {
                    declareBuilderUseCases.insertLiveDeliveryState(event.liveBuilderState)
                }
            }

            DeclareBuilderEvents.DeleteLiveBuilderState -> {declareBuilderUseCases.deleteLiveBuilderState()}

            is DeclareBuilderEvents.OnComparableWorkingPlatformPick -> {
             //  workingPlatform.update { declareBuilderUseCases.getWorkingPlatformById(event.workingPlatId)}

            }

            is DeclareBuilderEvents.OnArchiveComparableMenuPick -> {
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

            is DeclareBuilderEvents.GetComparableMenuAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = declareBuilderUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a,workingPlat = event.workingPlatform)
                    if (b.totalTime == -1f) {
                        //send a snack bar message to the user , there is no match data
                        //to present from the local data archive for $event.workingplatform pick...
                        onDeclareBuilderEvent(
                            DeclareBuilderEvents.SendUiMessage(
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

            is DeclareBuilderEvents.SendUiMessage -> {
                viewModelScope.launch {
                    uiMessage.send(event.mess)
                }
            }

            DeclareBuilderEvents.OnCloseComparableMenu -> {
                _uiState.update { it.copy(showComparableMenu = false) }
            }

            DeclareBuilderEvents.OnOpenComparableMenu -> {
                val a = declareBuilderUseCases.getAllTimeMonthData.invoke(liveBuilderState.value.workingPlatform)

                val b = getAllTimeSumObj(a,_uiState.value.comparableObj.platform)

                _uiState.update { it.copy(
                    comparableMenuData = b, showComparableMenu = true
                ) }
            }

            is DeclareBuilderEvents.GetComparableStatistics -> {

                val theData = declareBuilderUseCases.getWorkSessionStatisticsData(event.platform)


                if(theData.totalTime==-1f){

                    onDeclareBuilderEvent(
                        DeclareBuilderEvents.SendUiMessage(
                        "There is no match data to present from the local data archive for the ${event.platform} working platform"))
                }else {
                    _uiState.update {
                        it.copy(
                            comparableObj = theData
                        )
                    }
                }
            }

        }


    }

}