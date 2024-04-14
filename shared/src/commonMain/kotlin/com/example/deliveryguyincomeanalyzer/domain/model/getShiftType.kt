package com.example.deliveryguyincomeanalyzer.domain.model

fun getShiftType(dataShiftType:Int):String{
    return  when(dataShiftType){
        0->{"Morning"}
        1->{"Noon"}
        2->{"Night"}
        else -> {"Night"}
    }
}