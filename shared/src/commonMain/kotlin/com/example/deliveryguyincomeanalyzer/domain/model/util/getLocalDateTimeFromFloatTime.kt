package com.example.deliveryguyincomeanalyzer.domain.model.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getLocalDateTimeFromFloatTime(time:Float):LocalDateTime{

    val a = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

    var hour = (time).toInt()
    val minute = ((time-hour) * 60 ).toInt()

    if(hour > 23)
        hour-=24

    return LocalDateTime(date = a, time = LocalTime(hour,minute))

}