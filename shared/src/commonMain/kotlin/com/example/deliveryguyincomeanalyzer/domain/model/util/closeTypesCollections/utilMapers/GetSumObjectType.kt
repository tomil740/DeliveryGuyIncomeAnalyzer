package com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers

import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType

fun getSumObjectType(sumObjType:Int):String{
    return when(sumObjType){
        1->{
            SumObjectsType.MonthSum.name}
        2->{
            SumObjectsType.WorkSession.name}
        4->{"Morning"}
        5->{"Noon"}
        6->{"Night"}
        else->{""}
    }
}