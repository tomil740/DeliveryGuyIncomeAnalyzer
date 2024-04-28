package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

sealed class ObjectItemEvents {
    data class GetMonthSum(val theMonth:String):ObjectItemEvents()
    //we will define the comparable picker events ... to this screen
    data class GetComparableStatistics(val platform:String):ObjectItemEvents()
    data class InitializeAnObject(val theSum:SumObjectInterface):ObjectItemEvents()
    data class GetValueAllArchive(val workingPlatform: String):ObjectItemEvents()
    data class GetComparableMenuAllArchive(val workingPlatform: String):ObjectItemEvents()
    data object OnCloseComparableMenu:ObjectItemEvents()
    data object OnOpenComparableMenu:ObjectItemEvents()
    data class OnArchiveComparableMenuPick(val obj:SumObjectInterface?):ObjectItemEvents()
    data class OnValueWorkingPlatformPick(val workingPlatformId:String):ObjectItemEvents()
    data class  SendUiMessage(val mess:String):ObjectItemEvents()
    //data object OnMyStatWorkingPlatformMenu : ObjectItemEvents()
    //data object OnGeneralStatWorkingPlatformMenu : ObjectItemEvents()
}