package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

sealed class ObjectItemEvents {
    data class GetMonthSum(val theMonth:String):ObjectItemEvents()
    //we will define the comparable picker events ... to this screen
    data class GetComparableStatistics(val platform:String):ObjectItemEvents()
    data class InitializeAnObject(val theSum:SumObjectInterface):ObjectItemEvents()
    data class GetValueAllArchive(val workingPlatform: String):ObjectItemEvents()
    data class GetComparableMenuAllArchive(val workingPlatform: String):ObjectItemEvents()
    data object OnCloseMenu:ObjectItemEvents()
    data object OnOpenMenu:ObjectItemEvents()
    data class OnMenuPick(val obj:SumObjectInterface):ObjectItemEvents()


}