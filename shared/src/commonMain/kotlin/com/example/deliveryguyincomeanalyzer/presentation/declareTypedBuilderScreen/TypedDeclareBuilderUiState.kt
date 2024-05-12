package com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.TypeBuilderState
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import kotlinx.coroutines.channels.Channel

data class TypedDeclareBuilderUiState(
    val typeBuilderState: TypeBuilderState,
    val totalIncome : Float,
    val currentSum: SumObjectInterface,
    val comparableObj : SumObjectInterface,
    val uiMessage : Channel<String>,
    val errorMes:String,
    val workingPlatformRemoteMenu : List<WorkingPlatformOptionMenuItem>,
    val workingPlatformCustomMenu : List<WorkingPlatformOptionMenuItem>,
    val comparableMenuData : SumObjectInterface,
    val showComparableMenu : Boolean
)
