package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

/*
GetDeclareShifts description : V
this function get the declare data and sums it according to the working platform shifts frame , then according to the
data of our declare and each shift frame we will add the matched time frame DataPerHourDomain objects list
at the bootm line : we will get an list of the full shift data for each shift matched to our declare data , and a reserve
dataPerHour list of all the regular hour that dose not belong to any shift frame time range
 */

class GetDeclareShifts {

    operator fun invoke(
        dataPerHourDomain: List<DataPerHourDomain>,
        liveBuilderState: LiveBuilderState,
        shiftsFrames: List<ShiftFrame>
    ): ResultObj{

        val dataSum = mutableListOf<ShiftDomain>()
        var unUseDataPerHourDomain = mutableListOf<DataPerHourDomain>()


        for (shiftFrame in shiftsFrames) {
            var theStart = shiftFrame.startTime
            var theEnd = shiftFrame.endTime


            if(liveBuilderState.startTime > theStart)
                theStart = liveBuilderState.startTime

            if(liveBuilderState.endTime < theEnd)
                theEnd = liveBuilderState.endTime


            // if(theStart >= theEnd || getTimeDifferent(theStart.time,theEnd.time) < 4 ){
                //need to skip this eligal shift ...
           //     continue
          //  }
            var deliveries = 0
            var extras = 0f

            for (t in liveBuilderState.deliversItem) {
                if (t.time >= theStart && t.time < shiftFrame.endTime) {
                    deliveries += 1
                    extras += t.extra

                }
                /*
                else if (shiftFrame.name == "Night" && shiftFrame.endTime.date != shiftFrame.startTime.date) {
                    //when we will pass 00 we will nedd to use that
                    if (t.time.time <= shiftFrame.endTime.time) {
                        deliveries += 1
                        extras += t.extra

                    }
                }

                 */
            }
            val theShiftDataPerHourDomain = mutableListOf<DataPerHourDomain>()

            for (i in dataPerHourDomain) {
                var checkEndTime = theEnd.time.hour
                if (shiftFrame.name == "Night" && theStart.date != theEnd.date)
                    checkEndTime+=24

                if (i.hour >= theStart.hour && i.hour < checkEndTime) {
                    theShiftDataPerHourDomain.add(i)
                } else if (theShiftDataPerHourDomain.isNotEmpty() && i.hour+24 <= checkEndTime) {
                    //when we will pass 00 we will nedd to use that
                        theShiftDataPerHourDomain.add(i)
                }
                else{
                    unUseDataPerHourDomain.add(i)
                }
            }

            val timeSum = getTimeDifferent(theStart.time, theEnd.time)

            dataSum.add(
                ShiftDomain(
                    workingPlatform = liveBuilderState.workingPlatform,
                    shiftType = shiftFrame.name,
                    startTime = theStart.time,
                    endTime = theEnd.time,
                    time = timeSum,
                    extraIncome = extras,
                    baseIncome = liveBuilderState.baseWage*timeSum,
                    delivers = deliveries,
                    dataPerHour = theShiftDataPerHourDomain,
                )
            )
        }
        val theUnusedDataPerHourDomain = mutableListOf<DataPerHourDomain>()

        for(i in unUseDataPerHourDomain){
            var counter = 0
            for(j in unUseDataPerHourDomain){
                if (i.hour == j.hour)
                    counter+=1
            }
            if (counter==3) {
                var add = true
                for (j in theUnusedDataPerHourDomain) {
                    if(j.hour == i.hour)
                        add=false
                }
                if (add)
                    theUnusedDataPerHourDomain.add(i)
            }
        }

        if(dataSum.isEmpty())
            unUseDataPerHourDomain = dataPerHourDomain.toMutableList()

        return ResultObj(
            theShifts = dataSum,
            unUsedData = theUnusedDataPerHourDomain
        )
    }

    fun getDeclareShiftsTest() : ResultObj {

        /*
        val a = GetDeclareShifts().getDeclareShiftsTest()
                    for (i in a) {
                        Log.i("tt", "start ${i.startTime} end ${i.endTime} and the ${i.time} and the ${i.shiftType}")
                    }
                    for this simple check the function work prety well , need to implement an real test in future
         */

       return this.invoke(
            dataPerHourDomain = listOf(DataPerHourDomain(8,50f,30f,5), DataPerHourDomain(9,50f,30f,5),DataPerHourDomain(10,50f,30f,5),DataPerHourDomain(11,50f,30f,5),
                DataPerHourDomain(12,50f,30f,5), DataPerHourDomain(13,50f,30f,5),DataPerHourDomain(14,50f,30f,5),DataPerHourDomain(15,50f,30f,5),
               DataPerHourDomain(16,50f,30f,5),DataPerHourDomain(17,50f,30f,5),DataPerHourDomain(18,50f,30f,5),
            DataPerHourDomain(19,50f,30f,5), DataPerHourDomain(20,50f,30f,5),DataPerHourDomain(21,50f,30f,5),DataPerHourDomain(22,50f,30f,5),
            DataPerHourDomain(23,50f,30f,5), DataPerHourDomain(0,50f,30f,5),DataPerHourDomain(1,50f,30f,5),DataPerHourDomain(2,50f,30f,5),
                DataPerHourDomain(3,50f,30f,5), DataPerHourDomain(4,50f,30f,5),DataPerHourDomain(5,50f,30f,5),DataPerHourDomain(6,50f,30f,5),),

            liveBuilderState = LiveBuilderState(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
                totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
                workingPlatform = "Wolt", baseWage = 35f, extras = 55f, delivers = 5,
                deliversItem = listOf(
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5), time = LocalTime(17,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5),time = LocalTime(18,40)), extra = 150f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5),time = LocalTime(21,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,6),time = LocalTime(2,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,6),time = LocalTime(4,40)), extra = 150f),
                )),
            shiftsFrames = listOf(ShiftFrame("morrning", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,9,30), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,15,0)),
                ShiftFrame("Noon", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,16,0), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,22,0)),
                        ShiftFrame("Night", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,22,0), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,4,0)))
        )
    }


}

data class ResultObj(
    val theShifts : List<ShiftDomain>,
    val unUsedData : List<DataPerHourDomain>
)









