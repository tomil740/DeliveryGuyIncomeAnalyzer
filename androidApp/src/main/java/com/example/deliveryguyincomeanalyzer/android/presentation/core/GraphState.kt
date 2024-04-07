package com.example.deliveryguyincomeanalyzer.android.presentation.core

data class GraphState(
    val ogLst:List<Float> ,
    val dataLst:List<Float> = ogLst,
    val listStartTime:Int=0,
    val listEndTime:Int=dataLst.size,
    val startTime: Int = listStartTime,
    val endTime:Int=listEndTime
){

    fun getColumnSize():Float {
        var columns: Float = 0f
        for (i in dataLst) {
            if (i > columns)
                columns = i
        }
        return columns
    }
    //return an update cope of the object
    fun UpdateGraphData(startTime1: Int= startTime,endTime1: Int=endTime):GraphState{
        val result:MutableList<Float> = mutableListOf()
        var counter = listStartTime
        for (i in ogLst) {
            if(counter>=startTime1 && counter<=endTime1-1)
                result.add(i)

            counter++
        }
        if (result.isEmpty() ) {
            return this
        }

        return this.copy(dataLst=result,startTime=startTime1, endTime = endTime1)

    }

    fun getStartOptionsLst():List<Int>{
        var optionLst:MutableList<Int> = mutableListOf()
        for (i in 0..25){
            if(i>=listStartTime && i<=endTime&&i<endTime)
                optionLst.add(i)
        }
        return optionLst
    }
    fun getEndOptionsLst():List<Int>{
        var optionLst:MutableList<Int> = mutableListOf()
        for (i in 0..25){
            if(i>=startTime && i<=listEndTime&&i>startTime)
                optionLst.add(i)
        }
        return optionLst

    }

    fun getAverage():Float{
        var sum = 0f
        for (i in dataLst){
            sum+=i
        }
       return (sum/dataLst.size.toFloat())
    }

    fun getIncomeSum():Float{
        var a = 0f
        for (i in dataLst){
            a+=i
        }
        return a
    }

}