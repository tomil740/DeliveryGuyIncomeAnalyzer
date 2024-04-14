package com.example.deliveryguyincomeanalyzer.domain.model.util

import kotlinx.datetime.LocalTime

fun getTimeDifferent(startTime: LocalTime, endTime: LocalTime):Float{
    var a: LocalTime

    if(endTime.hour < startTime.hour) {
        if ((endTime.minute < startTime.minute))
            a = LocalTime(
                hour = (24 - startTime.hour + endTime.hour),
                minute = 60 + (endTime.minute - startTime.minute)
            )
        else{
            a = LocalTime(24 - startTime.hour + endTime.hour, minute = (endTime.minute - startTime.minute))

        }

    } else if(endTime.minute < startTime.minute){
        a = LocalTime(hour = (endTime.hour - startTime.hour-1), minute = 60+(endTime.minute - startTime.minute))
    }else{
        a = LocalTime(endTime.hour - startTime.hour, minute = (endTime.minute - startTime.minute))
    }

    return (a.hour + (a.minute.toFloat()/60.0).toFloat())


}