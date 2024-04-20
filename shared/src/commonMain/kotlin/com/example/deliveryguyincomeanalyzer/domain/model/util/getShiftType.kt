package com.example.deliveryguyincomeanalyzer.domain.model.util

fun getShiftType(dataShiftType:Int):String{
    return  when(dataShiftType){
        0->{"Morning"}
        1->{"Noon"}
        2->{"Night"}
        else -> {"Night"}
    }
}

fun getShiftType(dataShiftType:String):Int{
    return  when(dataShiftType){
        "Morning"->0
        "Noon"->1
        "Night"->2
        else -> {2}
    }
}