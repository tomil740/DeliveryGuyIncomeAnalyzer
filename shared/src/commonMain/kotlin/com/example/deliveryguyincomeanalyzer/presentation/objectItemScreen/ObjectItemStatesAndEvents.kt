package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

data class ObjectItemStatesAndEvents(
    val uiState: ObjectItemUiState,
    val initializeAnObject:(SumObjectInterface)->Unit,
    val getMonthSum : () -> Unit,
    val onOpenMenu:()->Unit,
    val onCloseMenu:()->Unit,


    val onArchiveComparableMenuPick:(SumObjectInterface?)->Unit,
    val onMyStatPick:(String)->Unit,
    val onComparablePlatform:(String)->Unit,
    val onValueWorkingPlatform: (String)->Unit,
)
