package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

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

class DeclareBuilderScreenClass: Screen {
    @Composable
    override fun Content() {
        val a = getViewModel<DeclareBuilderViewmodel>()
        val state by a.state.collectAsState()
        val b = DeclareBuilderStatesAndEvents(
            uiState = state,
            onAddDeliveryItem = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnAddDeliveryItem(it))},
            onDeleteDeclare = {  },
            onDeleteDeliveryItem = {
                a.onDeclareBuilderEvent(
                    DeclareBuilderEvents.OnDeleteDeliveryItem(
                        it
                    )
                )
            },
           // OnMainPlatformPick = { a.onDeclareBuilderEvent(DeclareBuilderEvents.OnMainPlatformPick(it)) },
            onSubmitDeclare = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnSubmitDeclare) }
            , saveLiveBuilderState =   {a.onDeclareBuilderEvent(
                DeclareBuilderEvents.SaveLiveBuilderState(state.liveBuilderState))}
            , getLiveBuilderState ={a.onDeclareBuilderEvent(
                DeclareBuilderEvents.GetLiveBuilderState)}
                ,
            deleteLiveBuilderState = {a.onDeclareBuilderEvent(DeclareBuilderEvents.DeleteLiveBuilderState)},
            onPickWorkingPlatform = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnComparableWorkingPlatformPick(it))},
            onComparablePlatform = {a.onDeclareBuilderEvent(DeclareBuilderEvents.GetComparableMenuAllArchive(it))},
            onArchiveComparableMenuPick = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnArchiveComparableMenuPick(it))},
            onCloseComparableArchiveMenu = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnCloseComparableMenu)},
            onComparableStatPick = {a.onDeclareBuilderEvent(DeclareBuilderEvents.GetComparableStatistics(it))},
            onOpenComparableArchiveMenu = {a.onDeclareBuilderEvent(DeclareBuilderEvents.OnOpenComparableMenu)},
        )
        //when picked this item platform, we will navigate to the matched builder
        //in order to not do that on default values we changee it if its
       // if(b.uiState.liveBuilderState.workingPlatformId == "Add +")
         //   b.OnMainPlatformPick("Wolt")
        DeclareLiveBuilderScreen(declareBuilderStatesAndEvents = b, modifier = Modifier)

    }
}
