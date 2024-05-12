package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.ShiftFrame
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.ShiftDomain
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveDeliveryItem
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.TypeBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.util.getTimeDifferent
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.plus

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

        val theStartDate = shiftsFrames[0].startTime.date
        var morningS = shiftsFrames[0].startTime.hour+(shiftsFrames[0].startTime.minute/60f)
        var morningE=shiftsFrames[0].endTime.hour+(shiftsFrames[0].endTime.minute/60f)
        var noonS=shiftsFrames[1].startTime.hour+(shiftsFrames[1].startTime.minute/60f)
        var noonE=shiftsFrames[1].endTime.hour+(shiftsFrames[1].endTime.minute/60f)
        var nightS=shiftsFrames[2].startTime.hour+(shiftsFrames[2].startTime.minute/60f)
        var nightE=shiftsFrames[2].endTime.hour+(shiftsFrames[2].endTime.minute/60f)
        var counter = 0
        for (i in shiftsFrames) {
            var theVal = 0
            if (theStartDate < i.startTime.date){
                theVal+=24
            }
            when(counter){
                0->{morningS+=theVal}
                1->{noonS+=theVal}
                2->{nightS+=theVal}
            }
            theVal = 0
            if (theStartDate < i.endTime.date){
                theVal+=24
            }
            when(counter){
                0->{morningE+=theVal}
                1->{noonE+=theVal}
                2->{nightE+=theVal}
            }
            counter+=1
        }
        /*
        At this phase each variable havs an intger value of his type between 0-48
         */


        /*
        need to define which shifts should be define
         */
        var dataStart = liveBuilderState.startTime.date
        val dataSTime=liveBuilderState.startTime.time.hour+(liveBuilderState.startTime.time.minute/60f)
        var dataETime=liveBuilderState.endTime.time.hour+(liveBuilderState.endTime.time.minute/60f)
        if(dataStart < liveBuilderState.endTime.date){
            dataETime+=24
        }
        var dataMorningS = if(dataSTime >=morningS){dataSTime}else{morningS}
        val dataMorningE =if (dataETime >= morningE){morningE}else{dataETime}
        var dataNoonS = if(dataSTime >=noonS){dataSTime}else{noonS}
        val dataNoonE =if (dataETime >= noonE){noonE}else{dataETime}
        var dataNightS = if(dataSTime >=nightS){dataSTime}else{nightS}
        val dataNightE =if (dataETime >= nightE){nightE}else{dataETime}



        /*
        now to each value data object we will check the same , if it is at different date
        */
        /*
        allicate the matched data to each shift from each delivery
        The reason for do that instand of jsut using the allready calculated dataperhour accordingly , is because
        here I will get the most accurate data as possiable
         */
        var deliveries = 0
        var extras = 0f
        var deliveries1 = 0
        var extras1 = 0f
        var deliveries2 = 0
        var extras2 = 0f

        //in order of using this use case as well with generalStatistics, that dosnt have the delivery items
        //we will put another option to calculate the shift data by the dataPerhour (which is less accurate)
        if(liveBuilderState.deliversItem.isEmpty()){
            //if this is an general statistics (which means we have all of the hours per day)
            if(dataPerHourDomain.size >= 24) {
                //for this calculation we will make some use of an average data to any unfull hour of an shift by use of the closeset data average
                for (i in dataPerHourDomain) {
                    var theTime = i.hour.toFloat()

                    if (theTime in dataMorningS..<dataMorningE) {
                        deliveries += i.delivers
                        extras += i.extraIncome
                    } else if (theTime in dataNoonS..<dataNoonE) {
                        deliveries1 += 1
                        extras1 += i.extraIncome
                    } else if (theTime in dataNightS..<dataNightE) {
                        deliveries2 += i.delivers
                        extras2 += i.extraIncome
                    }
                }

                if ((dataMorningS.toInt()).toFloat() != dataMorningS) {
                    if (deliveries != 0 || extras != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataMorningS.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataMorningS % 1f

                        extras += theShare * matchData.extraIncome
                        deliveries += (theShare * matchData.delivers).toInt()
                    }
                }
                if ((dataMorningE.toInt()).toFloat() != dataMorningE) {
                    if (deliveries != 0 || extras != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataMorningE.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataMorningE % 1f

                        extras += theShare * matchData.extraIncome
                        deliveries += (theShare * matchData.delivers).toInt()
                    }
                }
                if ((dataNoonS.toInt()).toFloat() != dataNoonS) {
                    if (deliveries1 != 0 || extras1 != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataNoonS.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataNoonS % 1f

                        extras1 += theShare * matchData.extraIncome
                        deliveries1 += (theShare * matchData.delivers).toInt()
                    }
                }
                if ((dataNoonE.toInt()).toFloat() != dataNoonE) {
                    if (deliveries1 != 0 || extras1 != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataNoonE.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataNoonE % 1f

                        extras1 += theShare * matchData.extraIncome
                        deliveries1 += (theShare * matchData.delivers).toInt()
                    }
                }
                if ((dataNightS.toInt()).toFloat() != dataNightS) {
                    if (deliveries2 != 0 || extras2 != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataNightS.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataNightS % 1f

                        extras2 += theShare * matchData.extraIncome
                        deliveries2 += (theShare * matchData.delivers).toInt()
                    }
                }
                if ((dataNightE.toInt()).toFloat() != dataNightE) {
                    if (deliveries1 != 0 || extras1 != 0f) {
                        //this will be used only on general statistics when the list include all of the hours...
                        var theIndex = dataNightE.toInt() - 1
                        if (theIndex < 0)
                            theIndex = 0
                        if (theIndex > 23)
                            theIndex -= 24
                        val matchData = dataPerHourDomain[theIndex]
                        val theShare = dataNightE % 1f

                        extras2 += theShare * matchData.extraIncome
                        deliveries2 += (theShare * matchData.delivers).toInt()
                    }
                }
                //if its an typed declare
            }else{
                for (i in dataPerHourDomain) {
                    if (i.hour.toFloat() in dataMorningS .. dataMorningE) {
                        var theShare = 1f
                        if (i.hour == dataMorningS.toInt()) {
                            theShare = (dataMorningS % 1f)
                        } else if (i.hour == dataMorningE.toInt()) {
                            theShare = (dataMorningE % 1f)
                        }
                        deliveries += (i.delivers * theShare).toInt()+1
                        extras += i.extraIncome * theShare

                    }else if (i.hour.toFloat() in dataNoonS..dataNoonE) {
                        var theShare = 1f
                        if (i.hour == dataNoonS.toInt()) {
                            theShare = (dataNoonS % 1f)
                        } else if (i.hour == dataNoonE.toInt()) {
                            theShare = (dataNoonE % 1f)
                        }
                        deliveries1 += (i.delivers * theShare).toInt()+1
                        extras1 += i.extraIncome * theShare
                    } else if (i.hour.toFloat() in dataNightS..dataNightE) {
                        var theShare = 1f
                        if (i.hour == dataNightS.toInt()) {
                            theShare = (dataNightS % 1f)
                        } else if (i.hour == dataNightE.toInt()) {
                            theShare = (dataNightE % 1f)
                        }
                        deliveries2 += (i.delivers * theShare).toInt()+1
                        extras2 += i.extraIncome * theShare
                    }
                }
            }

        }else {
            for (i in liveBuilderState.deliversItem) {
                var theTime = i.time.hour + (i.time.minute / 60f)
                if (dataStart < i.time.date) {
                    theTime += 24
                }
                if (theTime in dataMorningS..<dataMorningE) {
                    deliveries += 1
                    extras += i.extra
                } else if (theTime in dataNoonS..<dataNoonE) {
                    deliveries1 += 1
                    extras1 += i.extra
                } else if (theTime in dataNightS..<dataNightE) {
                    deliveries2 += 1
                    extras2 += i.extra
                }
            }
        }
        val theDataSum = mutableListOf<ShiftDomain>()
        /*
        now we need to connect the data per hour matched objects
         */
        val a = getShiftDomain(shiftYpe="Morning",sumE = dataMorningE, sumS = dataMorningS, liveBuilderState = liveBuilderState,
            deliveries = deliveries, extras = extras, dataPerHourDomain = dataPerHourDomain)
        if(a!=null) {
            theDataSum.add(a!!)
        }
        val b = getShiftDomain(shiftYpe="Noon",sumE = dataNoonE, sumS = dataNoonS, liveBuilderState = liveBuilderState,
            deliveries = deliveries1, extras = extras1, dataPerHourDomain = dataPerHourDomain)
        if(b!=null) {
            theDataSum.add(b!!)
        }
        val c = getShiftDomain(shiftYpe="Night",sumE = dataNightE, sumS = dataNightS, liveBuilderState = liveBuilderState,
            deliveries = deliveries2, extras = extras2, dataPerHourDomain = dataPerHourDomain)
        if(c!=null) {
            theDataSum.add(c!!)
        }

        if(theDataSum.isEmpty()){
            return ResultObj(
                theShifts = theDataSum,
                unUsedData = dataPerHourDomain)
        }
        var totalShiftTimeS =theDataSum.first().dataPerHour.first().hour


        var totalShiftTimeE = theDataSum.last().dataPerHour.last().hour

        if(totalShiftTimeE<totalShiftTimeS)
            totalShiftTimeE+=24


        val unUsedDataPerHour= mutableListOf<DataPerHourDomain>()

        var aftermid = false
        for (i in dataPerHourDomain) {
            var theHour = i.hour
            if (i.hour == 0)
                aftermid = true
            if(aftermid)
                theHour+=24

            if (theHour > totalShiftTimeE || theHour< totalShiftTimeS)
                unUsedDataPerHour.add(i)
        }

        return ResultObj(
            theShifts = theDataSum,
            unUsedData = unUsedDataPerHour)


    }
    fun getShiftDomain(shiftYpe : String,sumS:Float,sumE:Float,liveBuilderState:LiveBuilderState,deliveries:Int,extras:Float,
                       dataPerHourDomain: List<DataPerHourDomain>):ShiftDomain?{
        if(sumE - sumS >= 4) {
            val theShiftDataPerHourDomain = mutableListOf<DataPerHourDomain>()
            for (i in dataPerHourDomain) {
                if(i.hour.toFloat() in sumS..<sumE){
                    theShiftDataPerHourDomain.add(i)
                }
            }
            return ShiftDomain(
                workingPlatform = liveBuilderState.workingPlatform,
                shiftType = shiftYpe,
                startTime = fromSumIntToLocal(sumS,liveBuilderState.startTime.date),
                endTime = fromSumIntToLocal(sumE,liveBuilderState.startTime.date),
                time = sumE - sumS,
                extraIncome = extras,
                baseIncome = liveBuilderState.baseWage * (sumE - sumS),
                delivers = deliveries,
                dataPerHour = theShiftDataPerHourDomain,
            )

        }
        return null
    }
    fun fromSumIntToLocal(theVal:Float,theDate:LocalDate):LocalDateTime{
        var theVal1 = theVal
        var theDate1 = theDate
        var hour = theVal.toInt()
        if(hour>=24){
            theVal1-=24
            hour-=24
            theDate1 = theDate.plus(DatePeriod(days = 1))
        }
        val minute = ((theVal1-hour)*60).toInt()
        return LocalDateTime(date = theDate1, time = LocalTime(hour,minute))
    }


/*



        //need to sync the dates togther between the shifts frame form the abstract obj to the spesfic builder data ...
        var startDate:LocalDateTime = shiftsFrames[0].startTime
        val newFrame = mutableListOf<ShiftFrame>()
        for (i in shiftsFrames){
            if(startDate < i.startTime){
                newFrame.add(i.copy(startTime = LocalDateTime(date = typeBuilderState.startTime.date.plus(DatePeriod(days = 1)),
                    time = i.startTime.time)))
            }else{
                newFrame.add(i.copy(startTime = LocalDateTime(date = typeBuilderState.startTime.date,
                    time = i.startTime.time)))
            }
            if(startDate < i.endTime){
                newFrame.add(i.copy(endTime =LocalDateTime(date = typeBuilderState.startTime.date.plus(DatePeriod(days = 1)),
                    time = i.endTime.time)))
            }else{
                newFrame.add(i.copy(endTime = LocalDateTime(date = typeBuilderState.startTime.date,
                    time = i.endTime.time)))
            }
        }

        val dataSum = mutableListOf<ShiftDomain>()
        var unUseDataPerHourDomain = mutableListOf<DataPerHourDomain>()


        for (shiftFrame in newFrame) {
            var theStart = shiftFrame.startTime
            var theEnd = shiftFrame.endTime


            if(typeBuilderState.startTime > theStart)
                theStart = typeBuilderState.startTime

            if(typeBuilderState.endTime < theEnd)
                theEnd = typeBuilderState.endTime


            // if(theStart >= theEnd || getTimeDifferent(theStart.time,theEnd.time) < 4 ){
                //need to skip this eligal shift ...
           //     continue
          //  }
            var deliveries = 0
            var extras = 0f

            for (t in typeBuilderState.deliversItem) {
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
                    workingPlatformId = typeBuilderState.workingPlatformId,
                    shiftType = shiftFrame.name,
                    startTime = theStart,
                    endTime = theEnd,
                    time = timeSum,
                    extraIncome = extras,
                    baseIncome = typeBuilderState.baseWage*timeSum,
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

 */

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
            shiftsFrames = listOf(
                ShiftFrame("morrning", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,9,30), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,15,0)),
                ShiftFrame("Noon", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,16,0), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,22,0)),
                        ShiftFrame("Night", startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 4,22,0), LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,4,0))
            )
        )
    }


}

data class ResultObj(
    val theShifts : List<ShiftDomain>,
    val unUsedData : List<DataPerHourDomain>
)









