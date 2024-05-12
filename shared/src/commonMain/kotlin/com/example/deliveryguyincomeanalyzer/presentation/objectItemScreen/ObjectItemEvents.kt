package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util.ValueStatEventData

sealed class ObjectItemEvents {
    data object OnCloseComparableMenu:ObjectItemEvents()
    data object OnOpenComparableMenu:ObjectItemEvents()
    data class  SendUiMessage(val mess:String):ObjectItemEvents()
    data object UpdateRemoteSumObjStatistics:ObjectItemEvents()


    data class GetComparableMenuAllArchive(val workingPlatform: String):ObjectItemEvents()
    data class OnArchiveComparableMenuPick(val obj:SumObjectInterface?):ObjectItemEvents()
    data class GetComparableUserStatistics(val platform:String):ObjectItemEvents()
    data class GetComparableGeneralStatistics(val platform:String):ObjectItemEvents()

    data class InitializeAnObject(val theSum:SumObjectInterface):ObjectItemEvents()
    data class GetValueAllArchive(val workingPlatform: String):ObjectItemEvents()
    data class GetValueGeneralStatistics(val uiData: String):ObjectItemEvents()
    data class GetValueLocalStatistics(val uiData: String):ObjectItemEvents()

}