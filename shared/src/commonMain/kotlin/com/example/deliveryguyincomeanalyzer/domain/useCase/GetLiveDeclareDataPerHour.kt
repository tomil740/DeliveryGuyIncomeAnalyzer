package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month


/*
TheTime :
an helper data class to define our target object time range
* set and update them in one focused object class
 */
data class TheTime (
    var currentTime:Int,
    val startTime:Int,
    val endTime:Int
) {
    fun setTheCurrentTime(currentTime1: Int) {
        if (currentTime1 in startTime..endTime)
            currentTime = currentTime1
        else currentTime = endTime + 1

    }
}
class GetLiveDeclareDataPerHour {

    /*
    GetLiveDeclareDataPerHour
    * need to make sure we are in the time hours range all of the time
    * need to make sure we will initialize our object in every time change ...
     */

    /*
    GetLiveDeclareDataPerHour  ^
    will get the delivers items of the matched declare and the base income , and return the matched DataPerHourDomain list from them
    * calculate the list of hour range we need to make from the builder data , and translate edge case to compile
      together with some compeatabelity function

    * calculate each hour object (calculate above) from our declare data into mathced cronologics list
       go over each delivery item while checking if matche to the current object nd sum its data accordingly at the matched fields
     */
    operator fun invoke(liveBuilderState: LiveBuilderState, baseIncome: Float): List<DataPerHourDomain> {

        val startTime1 = liveBuilderState.startTime.time
        val endTime1 = liveBuilderState.endTime.time

        var startTime = liveBuilderState.startTime.time.hour
        var endTime = liveBuilderState.endTime.time.hour
        if (liveBuilderState.startTime.date != liveBuilderState.endTime.date) {
            endTime += 24
        }

        if (startTime1.minute > 4)
            startTime += 1

        if (endTime1.minute > 4)
            endTime -= 1

        val hoursObjects = mutableListOf<DataPerHourDomain>()

        /*
        need to run on all over the delivery item on each level at the time
         */

        var extras = 0f
        var deliveries = 0
        val currentTime = TheTime(currentTime = startTime, startTime = startTime, endTime = endTime)
        for (deliver in liveBuilderState.deliversItem) {
            var deliverTime = deliver.time.hour

            if(liveBuilderState.startTime.date != deliver.time.date){
                deliverTime = deliver.time.hour+24
            }

            if (deliverTime == currentTime.currentTime) {
                //add to the current time obj
                extras += deliver.extra
                deliveries += 1
            } else if (deliverTime < currentTime.currentTime) {
                //just pass
            } else if (deliverTime > currentTime.currentTime) {
                while (currentTime.currentTime < deliverTime && currentTime.currentTime <= currentTime.endTime) {
                    hoursObjects.add(
                        DataPerHourDomain(
                            if(currentTime.currentTime >=24) {
                                currentTime.currentTime-24}else{currentTime.currentTime},
                            extras,
                            baseIncome,
                            deliveries
                        )
                    )
                    extras = 0f
                    deliveries = 0
                    currentTime.setTheCurrentTime((currentTime.currentTime + 1))
                }
                extras += deliver.extra
                deliveries += 1
            }
        }
        if(currentTime.currentTime <= currentTime.endTime) {
            hoursObjects.add(
                DataPerHourDomain(
                    if(currentTime.currentTime >=24) {
                        currentTime.currentTime-24}else{currentTime.currentTime},
                    extras,
                    baseIncome,
                    deliveries
                )
            )
        }
        return hoursObjects
    }



    fun test():List<DataPerHourDomain>{
        /*
        apply at the main activity...
        val a = GetLiveDeclareDataPerHour().test()
                    val b = GetDeclareShifts().getDeclareShiftsTest()
                    // Log.i("tt", "the ${a.startTime} and ${a.endTime} , and ${a.currentTime}")


                    for (i in a) {
                        Log.i("tt", "the ${i.hour} and ${i.delivers} , and ${i.extraIncome}")
                    }
                    for (i in b){
                        Log.i("tt","the ${i.shiftType} from ${i.startTime} tail ${i.endTime}")
                    }
         */
         return this.invoke(
            liveBuilderState = LiveBuilderState(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
                totalTime = getTimeDifferent(startTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, endTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
                workingPlatform = "Wolt", baseWage = 35f, extras = 55f, delivers = 5,
                deliversItem = listOf(
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5), time = LocalTime(17,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5),time = LocalTime(18,40)), extra = 150f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,5),time = LocalTime(21,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,6),time = LocalTime(2,40)), extra = 50f),
                    LiveDeliveryItem(LocalDateTime(date = LocalDate(2024,Month.APRIL,6),time = LocalTime(4,40)), extra = 150f),
                    )), baseIncome = 40f
                )
    }



}