package com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftsSumByType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType

fun isShiftSession(theType:String):SumObjectsType{
    val a = listOf("Morning","Noon","Night")
    for (i in a){
        if (theType == i)
            return SumObjectsType.ShiftSession
    }
    return SumObjectsType.valueOf(theType)
}