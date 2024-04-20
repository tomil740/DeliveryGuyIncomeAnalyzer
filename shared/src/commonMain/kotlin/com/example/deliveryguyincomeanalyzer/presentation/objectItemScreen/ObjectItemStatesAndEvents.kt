package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform

data class ObjectItemStatesAndEvents(
    val uiState: ObjectItemUiState,
    val initializeAnObject:(SumObjectInterface)->Unit,
    val getMonthSum : () -> Unit,
    val onOpenMenu:()->Unit,
    val onCloseMenu:()->Unit,


    val onMenuPick:(SumObjectInterface)->Unit,

    val onComparablePlatform:(String)->Unit,
    val onValueWorkingPlatform: (String)->Unit
)
