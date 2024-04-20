package com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen

import com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels.LiveBuilderState

sealed class DeclareBuilderEvents() {
    data class onAddDeliveryItem(val extra: Float) : DeclareBuilderEvents()
    data class onDeleteDeliveryItem(val id: Int) : DeclareBuilderEvents()
    data class onPlatformPick(val platform: String):DeclareBuilderEvents()
    object onSubmitDeclare : DeclareBuilderEvents()
    data class saveLiveBuilderState(val liveBuilderState: LiveBuilderState):DeclareBuilderEvents()
    object getLiveBuilderState:DeclareBuilderEvents()

    object deleteLiveBuilderState:DeclareBuilderEvents()
}