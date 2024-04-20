package com.example.deliveryguyincomeanalyzer.domain.model.theModels

data class ShiftsSumByType(
    val type:String = "Night",
    val totalShifts : Int,
    val shiftSum:SumObj,
    val shiftSums : List<SumObj>
)
/*
data class ShiftSum(
    override val shiftType : String,//(defined the matched shift type from the three)
    override val extraIncome : Float,
    override val baseIncome : Float,
    override val averageIncomePerDelivery : Float,
    override val averageIncomePerHour : Float,
    override val incomePerSpecificHour : GraphState?=null,//(this object includes the List <Flaot>  value data)
    override val platform: String,
    override val objectType:SumObjectsType,
    override val objectName: String,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    override val totalTime: Float,
    override val totalIncome: Float,
    override val delivers: Int,
    override val workPerHour: GraphState?,
    override val subObjName: String,
    override val averageIncomeSubObj:Float,
    override val subObjects:List<SumObjectInterface>?,
    override val shiftsSumByType : List<ShiftsSumByType>?,
):SumObjectInterface



 */