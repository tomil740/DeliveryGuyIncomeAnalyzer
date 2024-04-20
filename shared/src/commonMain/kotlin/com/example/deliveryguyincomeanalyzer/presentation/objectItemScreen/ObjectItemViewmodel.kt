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





    private val _uiState = MutableStateFlow(ObjectItemUiState(objectValueSum = comparedObj.value,
        objectComparableSum = comparedObj.value, comparableDataMenu = comparedObj.value, showMenu = false))

    val uiState = combine(valuedObj,comparedObj,_uiState){
        valueObj,comparedObj,_uiState  ->
       _uiState.copy(objectComparableSum = comparedObj, objectValueSum = valueObj
           /*
           objectValueSum =
           SumObj(startTime = valueObj.startTime, endTime =  valueObj.endTime,
               totalTime = valueObj.totalTime,
               platform = valuedObjPlatform,
               baseIncome = valueObj.baseIncome, extraIncome = valueObj.extraIncome, totalIncome = valueObj.totalIncome, delivers = valueObj.delivers
               , averageIncomePerDelivery = valueObj.averageIncomePerDelivery,
               averageIncomePerHour =valueObj.averageIncomePerHour,
               averageIncomeSubObj = 4f,objectType = valueObj.objectType, shiftType = valueObj.shiftType, objectName = valueObj.objectName, subObjName = valueObj.subObjName,
               subObjects = valueObj.subObjects
           )

            */



       )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)





    init {
        viewModelScope.launch {
            onEvent(ObjectItemEvents.GetValueAllArchive(uiState.value.objectValueSum.platform))

            launch {
           valuedObj.collect{
               //this will execuate before the combine function of the state , cause geting the old data comparable...
               delay(10)
               onEvent(ObjectItemEvents.GetComparableStatistics(it.platform))
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
                       uiState.value.objectValueSum
                    }
                }
                comparedObj.update { theData }
            }

            ObjectItemEvents.OnCloseMenu -> {
                _uiState.update { it.copy(showMenu = false) }
            }

            is ObjectItemEvents.OnMenuPick -> {
                if (valuedObj.value.objectType == event.obj.objectType){
                    comparedObj.update { event.obj }
                    _uiState.update { it.copy(showMenu = false) }

                }else{
                    _uiState.update { it.copy(
                        comparableDataMenu = event.obj
                    ) }
                }
            }

            ObjectItemEvents.OnOpenMenu -> {
                val a = objectItemUseCases.getAllTimeMonthData.invoke(uiState.value.objectValueSum.platform)

                val b = getAllTimeSumObj(a)

                _uiState.update { it.copy(
                    comparableDataMenu = b, showMenu = true
                ) }
            }

            is ObjectItemEvents.GetValueAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a)

                    valuedObj.value =b

                    comparedObj.value=b

                    //_uiState.update { it.copy(objectValueSum = b) }
                }
            }

            is ObjectItemEvents.GetComparableMenuAllArchive -> {
                viewModelScope.launch {
                    //get all of the data in month objects
                    val a = objectItemUseCases.getAllTimeMonthData.invoke(event.workingPlatform)

                    val b = getAllTimeSumObj(a)

                    _uiState.update {
                        it.copy(
                            comparableDataMenu = b
                        )
                    }
                }
            }
        }
    }





















}