package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface

data class ObjectItemUiState(
    val objectValueSum: SumObjectInterface,
    val objectComparableSum : SumObjectInterface,
    val comparableDataMenu : SumObjectInterface,
    val showMenu : Boolean
)
