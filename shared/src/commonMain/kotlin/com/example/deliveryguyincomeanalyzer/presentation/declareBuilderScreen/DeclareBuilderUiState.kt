package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import kotlinx.coroutines.channels.Channel

data class DeclareBuilderUiState(
    val liveBuilderState: LiveBuilderState,
    val workingPlatform : WorkingPlatform,
    val totalIncome : Float,
    val currentSum: SumObjectInterface,
    val comparableObj : SumObjectInterface,
    val uiMessage : Channel<String>,
    val workingPlatformRemoteMenu : List<WorkingPlatformOptionMenuItem>,
    val workingPlatformCustomMenu : List<WorkingPlatformOptionMenuItem>,
    val comparableMenuData : SumObjectInterface,
    val showComparableMenu : Boolean
)
