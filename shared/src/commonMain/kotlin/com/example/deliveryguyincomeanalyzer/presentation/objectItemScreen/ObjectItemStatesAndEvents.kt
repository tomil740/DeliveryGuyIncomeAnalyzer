package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectsType
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.util.ValueStatEventData

data class ObjectItemStatesAndEvents(
    val uiState: ObjectItemUiState,
    val onOpenMenu:()->Unit,
    val onCloseMenu:()->Unit,
    val onComparablePlatform:(String)->Unit,



    val onArchiveComparableMenuPick:(SumObjectInterface?)->Unit,
    val initializeAnObject:(SumObjectInterface)->Unit,
    val onValueArchiveTopMenu: (String)->Unit,


    val onComparableUserStatPick:(String)->Unit,
    val onComparableGeneralStatisticsPick:(String)->Unit,
    val onValueUserStatPick:(String)->Unit,
    val onValueGeneralStatisticsPick:(String)->Unit
)
