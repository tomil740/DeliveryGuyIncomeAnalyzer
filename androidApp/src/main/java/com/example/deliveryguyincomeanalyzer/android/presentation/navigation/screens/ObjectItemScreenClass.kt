package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.ObjectItemScreen
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.ObjectItemEvents
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.ObjectItemStatesAndEvents
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.ObjectItemViewmodel
import org.koin.androidx.compose.getViewModel

class ObjectItemScreenClass(private val initializeObj: SumObjectInterface? = null
    ): Screen {
    @Composable
    override fun Content() {
        val a = getViewModel<ObjectItemViewmodel>()
        val state by a.uiState.collectAsState()
        val b = ObjectItemStatesAndEvents(uiState = state, getMonthSum = {},
            initializeAnObject = { a.onEvent(ObjectItemEvents.InitializeAnObject(it)) },
            onCloseMenu = {a.onEvent(ObjectItemEvents.OnCloseMenu)}, onOpenMenu = {a.onEvent(ObjectItemEvents.OnOpenMenu)},
            onMenuPick = {a.onEvent(ObjectItemEvents.OnMenuPick(it))},
            onValueWorkingPlatform = {a.onEvent(ObjectItemEvents.GetValueAllArchive(it))},
            onComparablePlatform = {a.onEvent(ObjectItemEvents.GetComparableMenuAllArchive(it))},
        )
        ObjectItemScreen(initializeObj = initializeObj,b,modifier = Modifier)
    }
}