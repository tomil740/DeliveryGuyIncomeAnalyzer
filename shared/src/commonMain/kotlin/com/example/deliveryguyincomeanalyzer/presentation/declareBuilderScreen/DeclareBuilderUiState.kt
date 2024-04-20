package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

data class DeclareBuilderUiState(
    val liveBuilderState: LiveBuilderState,
    val totalIncome : Float,
    val currentSum: SumObjectInterface,
    val comparableObj : SumObjectInterface
)
