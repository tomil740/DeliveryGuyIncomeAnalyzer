package com.example.deliveryguyincomeanalyzer.domain.model

import kotlinx.datetime.LocalDateTime

data class WorkSessionSum(
    override val platform : String,
    override val typeName : String = "Total",
    override val startTime : LocalDateTime,
    override val endTime : LocalDateTime,
    override val totalTime :Float,
    override val totalIncome : Float,
    override val baseIncome: Float,
    override val extraIncome : Float,
    override val delivers  : Int,
    override val averageIncomePerHour: Float,
    override val averageIncomePerDelivery : Float,
    override val shiftsSum : ShiftsSum? = null,//sums the shifts object under this session , on click will show there sum
    override val workPerHour : GraphState?=null,//(this object includes the List <Flaot>  value data) val
    override val incomePerSpecificHour : GraphState?=null, //(this object includes the List <Flaot>  value data)
):SumObject

