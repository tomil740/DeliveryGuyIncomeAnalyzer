package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.SumObject
import com.example.deliveryguyincomeanalyzer.domain.model.WorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

data class DeclareBuilderUiState(
    val liveBuilderState: LiveBuilderState,
    val totalIncome : Float,
    val currentSum: WorkSessionSum,
    val comparableObj : SumObject
)
