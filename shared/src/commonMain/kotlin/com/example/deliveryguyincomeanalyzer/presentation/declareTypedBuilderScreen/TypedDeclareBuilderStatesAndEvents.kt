package com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

data class TypedDeclareBuilderStatesAndEvents(
    val uiState : TypedDeclareBuilderUiState,
    val onSubmitDeclare : ()->Unit,
    val onDeleteDeclare : ()->Unit,
    val onValuePlatformPick: (String)->Unit,

    val onArchiveComparableMenuPick:(SumObjectInterface?)->Unit,
    val onComparablePlatform:(String)->Unit,
    val onComparableStatPick:(String)->Unit,
    val onOpenComparableArchiveMenu:()->Unit,
    val onCloseComparableArchiveMenu:()->Unit,

    val onExtra:(String)->Unit,
    val onStime:(String)->Unit,
    val onEtime:(String)->Unit,
    val onDate:(String)->Unit,
    val onDelivers:(String)->Unit

)
