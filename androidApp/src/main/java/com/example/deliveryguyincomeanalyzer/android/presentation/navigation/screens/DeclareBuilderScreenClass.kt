package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.DeclareLiveBuilderScreen
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderStatesAndEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderViewmodel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DeclareBuilderScreenClass: Screen {
    @Composable
    override fun Content() {
        val a = getViewModel<DeclareBuilderViewmodel>()
        val state by a.state.collectAsState()
        val b = DeclareBuilderStatesAndEvents(
            uiState = state,
            onAddDeliveryItem = {a.onDeclareBuilderEvent(DeclareBuilderEvents.onAddDeliveryItem(it))},
            onDeleteDeclare = {  },
            onDeleteDeliveryItem = {
                a.onDeclareBuilderEvent(
                    DeclareBuilderEvents.onDeleteDeliveryItem(
                        it
                    )
                )
            },
            onPlatformPick = { a.onDeclareBuilderEvent(DeclareBuilderEvents.onPlatformPick(it))
            },
            onSubmitDeclare = {a.onDeclareBuilderEvent(DeclareBuilderEvents.onSubmitDeclare) }
            , saveLiveBuilderState =   {a.onDeclareBuilderEvent(
                DeclareBuilderEvents.saveLiveBuilderState(state.liveBuilderState))}
            , getLiveBuilderState ={a.onDeclareBuilderEvent(
                DeclareBuilderEvents.getLiveBuilderState)}
                ,
            deleteLiveBuilderState = {a.onDeclareBuilderEvent(DeclareBuilderEvents.deleteLiveBuilderState)}
        )
        //when picked this item platform, we will navigate to the matched builder
        //in order to not do that on default values we changee it if its
        if(b.uiState.liveBuilderState.workingPlatform == "Add +")
            b.onPlatformPick("Wolt")
        DeclareLiveBuilderScreen(declareBuilderStatesAndEvents = b, modifier = Modifier)

    }
}
