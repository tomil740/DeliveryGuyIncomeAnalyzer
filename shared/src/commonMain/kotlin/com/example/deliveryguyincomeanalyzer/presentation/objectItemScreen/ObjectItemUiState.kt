package com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen

import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import kotlinx.coroutines.channels.Channel

data class ObjectItemUiState(
    val objectValueSum: SumObjectInterface,
    val uiMessage : Channel<String>,
    val workingPlatformRemoteMenu : List<WorkingPlatformOptionMenuItem>,
    val workingPlatformCustomMenu : List<WorkingPlatformOptionMenuItem>,
    val objectComparableSum : SumObjectInterface,
    val comparableDataMenu : SumObjectInterface,
    val showComparableMenu : Boolean
)
