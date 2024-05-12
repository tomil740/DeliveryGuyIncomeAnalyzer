package com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers

import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType

fun getSumObjectIntType(sumObjType:String):Int{
    return when(sumObjType){
        SumObjectsType.MonthSum.name->{1
            }
        SumObjectsType.WorkSession.name->{
            2}
        SumObjectsType.ShiftSession.name->{
            3
        }
        "Morning"->{4}
        "Noon"->{5}
        "Night"->{6}

        else->{2}
    }
}