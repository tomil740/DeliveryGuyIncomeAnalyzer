package com.example.deliveryguyincomeanalyzer.domain.model

import kotlinx.datetime.LocalDateTime

interface SumObject {
    val platform: String
    val typeName: String
    val startTime: LocalDateTime
    val endTime: LocalDateTime
    val totalTime: Float
    val totalIncome: Float
    val baseIncome: Float
    val extraIncome: Float
    val delivers: Int
    val averageIncomePerHour: Float
    val averageIncomePerDelivery: Float
    val shiftsSum: ShiftsSum?
        //sums the shifts object under this session , on click will show there sum
    val workPerHour: GraphState?//(this object includes the List <Flaot>  value data) val
    val incomePerSpecificHour: GraphState?
//(this object includes the List <Flaot>  value data)
}

