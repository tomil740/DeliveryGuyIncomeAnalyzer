package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import com.example.deliveryguyincomeanalyzer.domain.useCase.DeclareBuilderUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.getDeliversData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class DeclareBuilderViewmodel(private val declareBuilderUseCases: DeclareBuilderUseCases): ViewModel() {
    /*
    comparedObj :
    an component of the uiState object , will be an workSession sum (at this screen) , we will get the average of them from our db archive ...
     */
    private val comparedObj: SumObjectInterface = SumObj(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f)
        , extraIncome = 300f, totalIncome = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/
                getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        objectType = SumObjectsType.WorkSession, shiftType = null, averageIncomeSubObj = 5f, objectName = "w", subObjName = "", averageTimeSubObj = 5f
    )

    private val workingPlatform =MutableStateFlow<WorkingPlatform>(WorkingPlatform("default", listOf(),44f)
    )

    /*
    deliversItems :
    an component of the uiState object represent the current deliveries item list , will be update from the ui and
    the data will be calculate to the object below
     */
    private var deliversItems = MutableStateFlow<List<LiveDeliveryItem>>(listOf())

    /*
   liveBuilderState :
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
        workingPlatform = workingPlatform.value,
        comparableObj = comparedObj,//just an fake workSesionSum , should be the average one
        workingPlatformRemoteMenu = listOf(),
        workingPlatformCustomMenu = listOf()
        )
    )
    init {
        // onDeclareBuilderEvent(DeclareBuilderEvents.OnWorkingPlatfomPick("Wolt-Center"))
        //this method will initalize our builderState ...
        onDeclareBuilderEvent(DeclareBuilderEvents.getLiveBuilderState)

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

                launch {
                    workingPlatform.collect{
                        if(it.isDefault){
                            //TODO()need to load a snack bar
                            withContext(Dispatchers.Main) {
                            }
                        }
                    }
                }

                //val a = declareBuilderUseCases.getLastWorkSessionSum.invoke().toWorkSessionSum()
               // _uiState.update { it.copy(currentSum = a) }

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
        combine(deliversItems,liveBuilderState, _uiState,workingPlatform) {
                deliversLst,liveBuilderState, state,workPlat ->
            //calculate the list sumarise data
            val a = getDeliversData(deliversLst)
            //update the builder state accordingly with the data of it as well
            val b =LiveBuilderState(startTime = liveBuilderState.startTime, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                totalTime =  getTimeDifferent(startTime = liveBuilderState.startTime.time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time),
                workingPlatform = workPlat.name, baseWage = workPlat.baseWage, extras =a.extras, delivers = a.delivers, deliversItem =a.deliversItem)
            val c = b.toWorkSessionSum()
            DeclareBuilderUiState(
                liveBuilderState=b,
                currentSum = c,
                totalIncome = c.totalIncome,
                comparableObj = state.comparableObj,
                workingPlatform = workPlat,
                workingPlatformCustomMenu = state.workingPlatformCustomMenu,
                workingPlatformRemoteMenu = state.workingPlatformRemoteMenu
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun onDeclareBuilderEvent(event:DeclareBuilderEvents) {
        when (event) {
            is DeclareBuilderEvents.onAddDeliveryItem -> {
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

            is DeclareBuilderEvents.onDeleteDeliveryItem -> {
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


            DeclareBuilderEvents.onSubmitDeclare ->{
                val a = LocalDate(2024,Month.APRIL,24)
                val b = LocalDate(2024,Month.APRIL,25)
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

                var extras = 0f
                for (i in theLst){
                    extras+=i.extra
                }
                val shifts = declareBuilderUseCases.getWorkingPlatformById("Wolt-North")

                    val fakeLiveBuilderState = LiveBuilderState(
                    startTime = LocalDateTime(date = a, time = LocalTime(14,30)), endTime = LocalDateTime(b,LocalTime(2,30)), totalTime = getTimeDifferent(startTime = LocalDateTime(date = a, time = LocalTime(16,30)).time, endTime = LocalDateTime(b,LocalTime(4,4)).time),
                    workingPlatform = "Wolt-North",
                    baseWage = shifts.baseWage,
                    extras = extras, delivers = theLst.size,
                    deliversItem = theLst)
                viewModelScope.launch {
                    //we will get all of the working platform data
                    //get working platform data
                    val dataPerHour =
                        declareBuilderUseCases.getDeclareDataPerHour(fakeLiveBuilderState,shifts.baseWage)

                    val declareShifts = declareBuilderUseCases.getDeclareShifts(
                        dataPerHour, fakeLiveBuilderState,shifts.shifts
                                /*
                        listOf(
                            ShiftFrame(
                                name = "Morning",
                                startTime = LocalDateTime(2024, Month.APRIL, 11, hour = 9, 0),
                                endTime = LocalDateTime(2024, Month.APRIL, 11, hour = 14, 0)
                            ),
                            ShiftFrame(
                                name = "Noon",
                                startTime = LocalDateTime(2024, Month.APRIL, 11, hour = 14, 0),
                                endTime = LocalDateTime(2024, Month.APRIL, 11, hour = 19, 0)
                            ),
                            ShiftFrame(
                                name = "Night",
                                startTime = LocalDateTime(2024, Month.APRIL, 11, hour = 19, 0),
                                endTime = LocalDateTime(2024, Month.APRIL, 12, hour = 0, 0)
                            )
                        )

                                 */
                    )
                    declareBuilderUseCases.insertWorkDeclare(fakeLiveBuilderState.toWorkSessionSum())
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
            is DeclareBuilderEvents.onPlatformPick -> {
                liveBuilderState.update { it.copy(workingPlatform = event.platform) }
            }

            DeclareBuilderEvents.getLiveBuilderState -> {
                viewModelScope.launch {
                    //get the current builder state to be setup
                    val a = declareBuilderUseCases.getLiveDeliveryState.invoke()
                    //to update the all around data
                    liveBuilderState.update {a}
                    //for updating the exact delivries sum data
                    deliversItems.update { a.deliversItem }
                }
            }

            is DeclareBuilderEvents.saveLiveBuilderState -> {
                viewModelScope.launch {
                    declareBuilderUseCases.insertLiveDeliveryState(event.liveBuilderState)
                }
            }

            DeclareBuilderEvents.DeleteLiveBuilderState -> {declareBuilderUseCases.deleteLiveBuilderState()}

            is DeclareBuilderEvents.OnWorkingPlatfomPick -> {
               workingPlatform.update { declareBuilderUseCases.getWorkingPlatformById(event.workingPlatId)}

            }
        }


    }

}