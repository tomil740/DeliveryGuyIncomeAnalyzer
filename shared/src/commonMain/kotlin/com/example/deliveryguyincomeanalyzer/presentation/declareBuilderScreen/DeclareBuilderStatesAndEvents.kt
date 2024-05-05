package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

data class DeclareBuilderStatesAndEvents(
    val uiState : DeclareBuilderUiState,
    val onAddDeliveryItem: (Float) -> Unit,
    val onDeleteDeliveryItem:(Int)->Unit,
   // val OnMainPlatformPick: (String)->Unit,
    val onSubmitDeclare : ()->Unit,
    val onDeleteDeclare : ()->Unit,
    val saveLiveBuilderState: (LiveBuilderState)->Unit,
    val getLiveBuilderState : ()->Unit,
    val deleteLiveBuilderState: ()-> Unit,
    val onPickWorkingPlatform: (String)->Unit,

    val onArchiveComparableMenuPick:(SumObjectInterface?)->Unit,
    val onComparablePlatform:(String)->Unit,
    val onComparableStatPick:(String)->Unit,
    val onOpenComparableArchiveMenu:()->Unit,
    val onCloseComparableArchiveMenu:()->Unit
)