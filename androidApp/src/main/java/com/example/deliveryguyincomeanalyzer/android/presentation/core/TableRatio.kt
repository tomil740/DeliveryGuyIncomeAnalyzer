package com.example.deliveryguyincomeanalyzer.android.presentation.core

data class TableRatio(
    val tableColumns : Int,
    val columnsRatio : Int
){
    fun getTextUnit(index:Int):String{
        //get an int value that must returned as 3 digits max , and two min (top value will be 99K)
        val a = (tableColumns - (index))*columnsRatio
        return when(a.toString().length){
            in 2..3 -> {
                a.toString()
            }

            1 -> {
                "0${a}"
            }

            4->{
                if(a.toString().get(1) != '0')
                    "${a/1000}.${a.toString().get(1)}K"
                else
                    "${a/1000}K"
            }

            5->{
                "${a/1000}K"
            }

            else->{
                "E"
            }
        }
    }
}
//115
    fun getRatio(graphsColumns:Float):TableRatio{
        var columnsRatio = 1//10
    //116
        var tableColumns = graphsColumns.toInt()+1//12

        while(tableColumns > 14){
            if (tableColumns < 40){
                columnsRatio*=5
                tableColumns = (tableColumns / 5)+1
            }else{
                columnsRatio*=10
                tableColumns = (tableColumns / 10)+1
            }
        }

        return TableRatio(tableColumns=tableColumns, columnsRatio = columnsRatio)
    }



