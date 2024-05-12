package com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

sealed class TypedDeclareBuilderEvents() {
    data class OnMainPlatformPick(val platform: String): TypedDeclareBuilderEvents()
    data object OnSubmitDeclare : TypedDeclareBuilderEvents()
    data class OnComparableWorkingPlatformPick(val workingPlatId : String): TypedDeclareBuilderEvents()
    data class  SendUiMessage(val mess:String): TypedDeclareBuilderEvents()
    data class OnArchiveComparableMenuPick(val obj: SumObjectInterface?): TypedDeclareBuilderEvents()
    data class GetComparableMenuAllArchive(val workingPlatform:String): TypedDeclareBuilderEvents()
    data object OnCloseComparableMenu: TypedDeclareBuilderEvents()
    data object OnOpenComparableMenu: TypedDeclareBuilderEvents()
    data class GetComparableStatistics(val platform:String): TypedDeclareBuilderEvents()
    data class OnStartTime(val sTime:String):TypedDeclareBuilderEvents()
    data class OnEndTime(val eTime:String):TypedDeclareBuilderEvents()
    data class OnDate(val date :String):TypedDeclareBuilderEvents()
    data class OnDelivers(val deliversVal:String):TypedDeclareBuilderEvents()
    data class OnExtra(val extraVal:String):TypedDeclareBuilderEvents()
}