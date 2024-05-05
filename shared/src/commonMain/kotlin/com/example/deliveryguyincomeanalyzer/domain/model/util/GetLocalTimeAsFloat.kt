package com.example.deliveryguyincomeanalyzer.domain.model.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

/*
we will take the time attributes , Hour and minutes values and convert them to an float number between 0-23.999
 */
fun getLocalTimeAsFloat(theVal:LocalTime):Float{
    val hours = theVal.hour
    val minute = theVal.minute.toFloat()*(1f/60f)
    return (hours+minute)
}