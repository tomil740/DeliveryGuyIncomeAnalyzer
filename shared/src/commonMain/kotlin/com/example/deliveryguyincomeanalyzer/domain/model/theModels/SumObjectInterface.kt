package com.example.deliveryguyincomeanalyzer.domain.model.theModels

import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDateTime

interface SumObjectInterface {
    val shiftType:String?
    var platform: String
    val objectType : SumObjectsType
    val objectName: String
    val startTime: LocalDateTime
    val endTime: LocalDateTime
    val totalTime: Float
    val totalIncome: Float
    val baseIncome: Float
    val extraIncome: Float
    val delivers: Int
    val averageIncomePerHour: Float
    val averageIncomePerDelivery: Float
    val workPerHour: GraphState?
    val incomePerSpecificHour: GraphState?
    val subObjName: String
    val averageIncomeSubObj:Float
    val averageTimeSubObj:Float
    val subObjects:List<SumObjectInterface>?
    val shiftsSumByType : List<ShiftsSumByType>?
}

data class SumObj(
    override val shiftType:String?=null,
    override var platform: String,
    override val objectType : SumObjectsType,
    override val objectName: String,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    override val totalTime: Float,
    override val totalIncome: Float,
    override val baseIncome: Float,
    override val extraIncome: Float,
    override val delivers: Int,
    override val averageIncomePerHour: Float,
    override val averageIncomePerDelivery: Float,
    override val workPerHour: GraphState?=null,
    override val incomePerSpecificHour: GraphState?=null,
    override val subObjName: String,
    override val averageIncomeSubObj:Float,
    override val averageTimeSubObj:Float,
    override val subObjects:List<SumObjectInterface>?=null,
    override val shiftsSumByType : List<ShiftsSumByType>?=null,
):SumObjectInterface


