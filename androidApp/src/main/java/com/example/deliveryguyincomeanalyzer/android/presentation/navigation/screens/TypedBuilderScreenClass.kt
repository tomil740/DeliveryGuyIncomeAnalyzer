package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.TypedDeclareBuilderScreen
import com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen.TypedDeclareBuilderEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen.TypedDeclareBuilderStatesAndEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareTypedBuilderScreen.TypedDeclareBuilderViewmodel
import org.koin.androidx.compose.getViewModel

class TypedBuilderScreenClass(): Screen {
    @Composable
    override fun Content() {
        val a = getViewModel<TypedDeclareBuilderViewmodel>()
        val state by a.state.collectAsState()
        val b = TypedDeclareBuilderStatesAndEvents(
            uiState = state,
            onDeleteDeclare = {  },
            onSubmitDeclare = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnSubmitDeclare) }
            ,
            onValuePlatformPick ={ a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnMainPlatformPick(it)) },
            onComparablePlatform = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.GetComparableMenuAllArchive(it))},
            onArchiveComparableMenuPick = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnArchiveComparableMenuPick(it))},
            onCloseComparableArchiveMenu = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnCloseComparableMenu)},
            onComparableStatPick = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.GetComparableStatistics(it))},
            onOpenComparableArchiveMenu = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnOpenComparableMenu)},
            onDate = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnDate(it))},
            onEtime = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnEndTime(it))},
            onExtra = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnExtra(it))},
            onStime = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnStartTime(it))},
            onDelivers = {a.onTypedDeclareBuilderEvent(TypedDeclareBuilderEvents.OnDelivers(it))}
        )
        //when picked this item platform, we will navigate to the matched builder
        //in order to not do that on default values we changee it if its
        // if(b.uiState.typeBuilderState.workingPlatformId == "Add +")
        //   b.onValuePlatformPick("Wolt")
        TypedDeclareBuilderScreen(typedDeclareBuilderStatesAndEvents = b, modifier = Modifier)

    }
}