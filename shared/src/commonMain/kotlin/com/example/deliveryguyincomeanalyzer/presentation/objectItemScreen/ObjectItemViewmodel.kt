package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getShiftType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import com.example.deliveryguyincomeanalyzer.domain.useCase.ObjectItemUseCases
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
   private  var comparedObj:MutableStateFlow<SumObjectInterface> =

       MutableStateFlow(SumObj(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f)
        , extraIncome = 300f, totalIncome = 6f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f+300f)/
                getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        averageIncomeSubObj = 4f,objectType = SumObjectsType.WorkSession, shiftType = null, objectName = "", subObjName = "", averageTimeSubObj = 5f
        )
    )

    private  var valuedObj:MutableStateFlow<SumObjectInterface> =
        MutableStateFlow(SumObj(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f)
        , extraIncome = 300f, totalIncome = 6f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time) *35f+300f)/
                getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        averageIncomeSubObj = 4f,objectType = SumObjectsType.WorkSession, shiftType = null, objectName = "", subObjName = "", averageTimeSubObj = 7f
    )
    )

    private val uiMessage = Channel<String>()

    private val _uiState = MutableStateFlow(ObjectItemUiState(objectValueSum = comparedObj.value,
        objectComparableSum = comparedObj.value, comparableDataMenu = comparedObj.value, showComparableMenu = false,
        workingPlatformRemoteMenu = listOf(), workingPlatformCustomMenu = listOf(), uiMessage = uiMessage
    ))

    val uiState = combine(valuedObj,comparedObj,_uiState){
        valueObj,comparedObj,_uiState  ->
        _uiState.copy(objectComparableSum = comparedObj, objectValueSum = valueObj,uiMessage=uiMessage)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        viewModelScope.launch {

            //onEvent(ObjectItemEvents.GetValueAllArchive(uiState.value.objectValueSum.platform))

            launch {
                valuedObj.collect{
               //this will execuate before the combine function of the state , cause geting the old data comparable...

               delay(10)
               //onEvent(ObjectItemEvents.GetComparableStatistics(it.platform))

                }
            }


            launch {
                objectItemUseCases.getWorkingPlatformMenu.invoke(false).collect { theLSt->
                    _uiState.update {it.copy(workingPlatformCustomMenu = theLSt)  }
                }
            }
            launch {
                objectItemUseCases.getWorkingPlatformMenu.invoke(true).collect { theLSt->
                    _uiState.update {it.copy(workingPlatformRemoteMenu = theLSt)}
                }
            }

        }
    }



    fun onEvent(event:ObjectItemEvents){
        when(event){
            is ObjectItemEvents.GetMonthSum//StatisticData...
            -> {

            }
            is ObjectItemEvents.InitializeAnObject -> {
                valuedObj.update {(event.theSum) }
            }

            is ObjectItemEvents.GetComparableStatistics -> {

                val theData = when(uiState.value.objectValueSum.objectType){

                    SumObjectsType.WorkSession -> {
                        objectItemUseCases.getWorkSessionStatisticsData(event.platform)
                    }

                    SumObjectsType.ShiftSession -> {
                        objectItemUseCases.getShiftTypeStatisticsData(event.platform, shiftType = getShiftType(uiState.value.objectValueSum.shiftType!!))
                    }

                    SumObjectsType.MonthSum -> {
                        val a = objectItemUseCases.getAllTimeMonthData.invoke(event.platform)
                        val monthSums = mutableListOf<WorkSumDomain>()

                        for (i in a){
                            if(i.data.size > 0)//the minimum set to the platform)
                                monthSums.add(objectItemUseCases.sumDomainData.getSummarizesDomainObject(i.data))
                        }

                        objectItemUseCases.sumDomainData.getAverageDomainObject(monthSums).toWorkSum().copy(objectName = "MyStatistics")

                    }

                    SumObjectsType.YearSum -> {
                        uiState.value.objectValueSum
                    }


                    SumObjectsType.AllTimeSum -> {
                        val a = objectItemUseCases.getAllTimeMonthData.invoke(event.platform)
                        val monthSums = mutableListOf<WorkSumDomain>()

                        for (i in a){
                            if(i.data.size > 0)//the minimum set to the platform)
                                monthSums.add(objectItemUseCases.sumDomainData.getSummarizesDomainObject(i.data))
                        }

                        objectItemUseCases.sumDomainData.getSummarizesDomainObject(monthSums).toWorkSum().copy(objectName = "MyStatistics")
                    }
                }
                if(theData.totalTime==-1f){

                    onEvent(ObjectItemEvents.SendUiMessage(
                        "There is no match data to present from the local data archive for the ${event.platform} working platform"))
                }else {
                    comparedObj.update { theData }
                }
            }

            ObjectItemEvents.OnCloseComparableMenu -> {
                _uiState.update { it.copy(showComparableMenu = false) }
            }

            is ObjectItemEvents.OnArchiveComparableMenuPick -> {
                if(event.obj==null){
                    _uiState.update { it.copy(showComparableMenu = false) }
                }
                else if (valuedObj.value.objectType == event.obj.objectType){
                    comparedObj.update { event.obj }
                    _uiState.update { it.copy(showComparableMenu = false) }

                }else{
                    _uiState.update { it.copy(
                        comparableDataMenu = event.obj
                    ) }
                }
            }

            ObjectItemEvents.OnOpenComparableMenu -> {
                val a = objectItemUseCases.getAllTimeMonthData.invoke(uiState.value.objectValueSum.platform)

                val b = getAllTimeSumObj(a)

                _uiState.update { it.copy(
                    comparableDataMenu = b, showComparableMenu = true
                ) }
            }

            is ObjectItemEvents.GetValueAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)
                    if(a.isEmpty()) {
                        onEvent(
                            ObjectItemEvents.SendUiMessage(
                                "There is no match data to present from the local data archive for the ${event.workingPlatform} working platform"
                            )
                        )
                    }else {
                        val b = getAllTimeSumObj(a, event.workingPlatform)

                        valuedObj.value = b

                        comparedObj.value = b
                    }
                }
            }

            is ObjectItemEvents.GetComparableMenuAllArchive -> {
                println("working->-.work ${event.workingPlatform}")
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a)
                    if (b.totalTime == -1f) {
                        //send a snack bar message to the user , there is no match data
                        //to present from the local data archive for $event.workingplatform pick...
                        onEvent(ObjectItemEvents.SendUiMessage(
                            "There is no match data to compare from the local data archive for the ${event.workingPlatform} working platform"))
                    }else {

                        _uiState.update {
                            it.copy(
                                comparableDataMenu = b
                            )
                        }
                    }
                }
            }
            is ObjectItemEvents.OnValueWorkingPlatformPick->{
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
        }
    }


















}