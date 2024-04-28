package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

data class DeclareBuilderStatesAndEvents(
    val uiState : DeclareBuilderUiState,
    val onAddDeliveryItem: (Float) -> Unit,
    val onDeleteDeliveryItem:(Int)->Unit,
   // val onPlatformPick: (String)->Unit,
    val onSubmitDeclare : ()->Unit,
    val onDeleteDeclare : ()->Unit,
    val saveLiveBuilderState: (LiveBuilderState)->Unit,
    val getLiveBuilderState : ()->Unit,
    val deleteLiveBuilderState: ()-> Unit,
    val onPickWorkingPlatform: (String)->Unit
)