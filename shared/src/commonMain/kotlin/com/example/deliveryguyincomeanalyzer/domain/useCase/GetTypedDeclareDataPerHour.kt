package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.TypeBuilderState
import com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.TimeUtilFunctions.addTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class GetTypedDeclareDataPerHour() {
    operator fun invoke (theTypedState:TypeBuilderState):List<DataPerHourDomain>{
        //start by defining the target hours result list ...
        val theResult = mutableListOf<DataPerHourDomain>()
        val sTime:LocalDateTime = if(theTypedState.startTime.minute!=0){addTimePeriod(theTime = theTypedState.startTime, toAdd = LocalTime(1,0))}else{theTypedState.startTime}
        val eTime:LocalDateTime = if(theTypedState.endTime.minute!=0){addTimePeriod(theTime = theTypedState.endTime, toAdd = LocalTime(1,0))}else{theTypedState.endTime}
        var theSTime = sTime.hour
        var theETime = eTime.hour
        if(sTime.date!=eTime.date)
            theETime+=24

        var extraPerHour = theTypedState.extras/theTypedState.totalTime
        var delivers = (theTypedState.delivers/theTypedState.totalTime).toInt()

        for (i in theSTime..theETime){
            var theHour = i
            if (i>=24)
                theHour-=24

            theResult.add(DataPerHourDomain(
                hour = theHour,
                baseIncome = theTypedState.baseWage,
                extraIncome = extraPerHour,
                delivers = delivers
            ))
        }

        return theResult

    }

}