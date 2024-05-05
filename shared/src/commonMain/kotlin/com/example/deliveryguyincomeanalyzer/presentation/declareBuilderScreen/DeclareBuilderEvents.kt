package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

sealed class DeclareBuilderEvents() {
    data class OnAddDeliveryItem(val extra: Float) : DeclareBuilderEvents()
    data class OnDeleteDeliveryItem(val id: Int) : DeclareBuilderEvents()
    data class OnMainPlatformPick(val platform: String):DeclareBuilderEvents()
    data object OnSubmitDeclare : DeclareBuilderEvents()
    data class SaveLiveBuilderState(val liveBuilderState: LiveBuilderState):DeclareBuilderEvents()
    data object GetLiveBuilderState:DeclareBuilderEvents()
    data object DeleteLiveBuilderState:DeclareBuilderEvents()
    data class OnComparableWorkingPlatformPick(val workingPlatId : String):DeclareBuilderEvents()
    data class  SendUiMessage(val mess:String): DeclareBuilderEvents()
    data class OnArchiveComparableMenuPick(val obj: SumObjectInterface?):DeclareBuilderEvents()
    data class GetComparableMenuAllArchive(val workingPlatform:String):DeclareBuilderEvents()
    data object OnCloseComparableMenu: DeclareBuilderEvents()
    data object OnOpenComparableMenu: DeclareBuilderEvents()
    data class GetComparableStatistics(val platform:String):DeclareBuilderEvents()


}