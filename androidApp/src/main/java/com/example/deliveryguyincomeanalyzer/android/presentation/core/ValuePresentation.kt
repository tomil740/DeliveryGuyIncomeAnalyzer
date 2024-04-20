package com.example.deliveryguyincomeanalyzer.android.presentation.core

fun valuePresentation(theVal:Float):String{
    var result = "$theVal"
    val workingValue=theVal.toInt()
    if(theVal > 999 || theVal < -999){
        val a = workingValue/1000
        val b = ((workingValue-(a*1000))/10)

        if(b < 10)
            result="${(a)}.0${b}"
        if(b >= 10){
            result = "${(a)}.${b}"
        }
        if (result.last() == '0')
           result = result.subSequence(0,result.length-1).toString()

        result+="K"
    }
    else{
        if(theVal-workingValue != 0f){
            result = "$workingValue.${(theVal-workingValue).toString().subSequence(2,3)}"
        }else{
            result = workingValue.toString()
        }
    }

    return  result
}