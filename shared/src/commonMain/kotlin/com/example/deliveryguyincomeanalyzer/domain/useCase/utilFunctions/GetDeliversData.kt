package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem

data class LiveDeliversSum(
    val extras:Float,
    val delivers:Int,
    val deliversItem: List<LiveDeliveryItem>
)
fun getDeliversData(deliversItem: List<LiveDeliveryItem>):LiveDeliversSum{
    var extras = 0f
    var delivers = 0
    for (i in deliversItem){
        extras+=i.extra
        delivers+=1
    }
    return LiveDeliversSum(extras,delivers,deliversItem)

}

