package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions.TimeUtilFunctions

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun addTimePeriod(theTime:LocalDateTime,toAdd:LocalTime,shouldAdd:Boolean=true):LocalDateTime{
    var hoursToAdd = toAdd.hour
    var minuteToAdd =toAdd.minute

    if (!shouldAdd){
        hoursToAdd*=(-1)
        minuteToAdd*=(-1)
    }


    var theMinute = theTime.time.minute + minuteToAdd
    if (theMinute > 59) {
        theMinute -= 59
        hoursToAdd+=1
    }
    else if(theMinute < 0){
        theMinute += 60
        hoursToAdd-=1
    }

    var theHour = theTime.time.hour + hoursToAdd
    if (theHour > 23) {
        theHour -= 23
        return LocalDateTime(date = theTime.date.plus(DatePeriod(days = 1)), time = LocalTime(hour = theHour, minute = theMinute))
    }

    if (theHour < 0){
        theHour+=23
        return LocalDateTime(date = theTime.date.minus(DatePeriod(days = 1)), time = LocalTime(hour = theHour, minute = theMinute))

    }

   return LocalDateTime(date = theTime.date, time = LocalTime(hour = theHour, minute = theMinute))

}