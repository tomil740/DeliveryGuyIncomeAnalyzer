package com.example.deliveryguyincomeanalyzer.domain.useCase.utilFunctions

import androidx.compose.runtime.Composable
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain

data class SumDataPerHour(
    val hour : Int,//(will be const between 1-24)
    val amount:Int,
    val extraIncome : Float,
    val baseIncome : Float, //(define specific hour if the platform will update that will stay the same …)
    val delivers : Int
)
fun getDataPerHourSum (workPerHour:List<DataPerHourDomain>,startTime:Int=0,endTime:Int=23):List<DataPerHourDomain>{
    val hourList = mutableListOf<SumDataPerHour>()
    for (i in startTime..endTime){
        hourList.add(
            SumDataPerHour(
                hour = i,
                amount = 0,
                delivers = 0,
                baseIncome = 0f,
                extraIncome = 0f
            )
        )
    }

    for (i in workPerHour){
        when(i.hour){
            0->{val index= 0
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            1->{val index= 1
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            2->{val index= 2
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            3->{val index= 3
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            4->{val index= 4
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            5->{val index= 5
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            6->{val index= 6
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            7->{val index= 7
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            8->{val index= 8
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            9->{val index= 9
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            10->{val index= 10
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            11->{val index= 11
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            12->{val index= 12
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            13->{val index= 13
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            14->{val index= 14
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            15->{val index= 15
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            16->{val index= 16
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            17->{val index= 17
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            18->{val index= 18
                val a= hourList[index]
            hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            19->{val index= 19
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            20->{val index= 20
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            21->{val index= 21
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            22->{val index= 22
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
            23->{val index= 23
                val a= hourList[index]
                hourList[index] = hourList[index].copy(extraIncome = a.extraIncome+i.extraIncome,
                    baseIncome =  a.baseIncome+i.baseIncome, delivers =  a.delivers+i.delivers, amount = a.amount+1)}
        }
    }

    val resultList = mutableListOf<DataPerHourDomain>()
    for (i in hourList){
        resultList.add(
            try {
            DataPerHourDomain(
                hour = i.hour,
                extraIncome = i.extraIncome/i.amount,
                baseIncome = i.baseIncome/i.amount,
                delivers = i.delivers/i.amount
            )}catch (e:Exception){DataPerHourDomain(hour = i.hour, extraIncome = i.extraIncome, baseIncome = i.baseIncome, delivers = i.delivers)}
        )
    }

    return resultList

}