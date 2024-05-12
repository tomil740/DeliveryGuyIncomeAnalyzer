package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObj
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

class DefaultData() {
    /*
   comparedObj :
   an component of the uiState object , will be an workSession sum (for default value only) , this object is just an simple helper value for deffualt initalization
    */
     var comparedObj: MutableStateFlow<SumObjectInterface> =

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

     var valuedObj: MutableStateFlow<SumObjectInterface> =
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
}

/*
declare builder vm fake comprqable obj
SumObj(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f)
        , extraIncome = 300f, totalIncome = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/
                getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        objectType = SumObjectsType.WorkSession, shiftType = null, averageIncomeSubObj = 5f, objectName = "w", subObjName = "", averageTimeSubObj = 5f,sumObjectSourceType = SumObjectSourceType.Archive
    )
 */