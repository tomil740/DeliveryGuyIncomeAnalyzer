package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.DeclareLiveBuilderScreen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.ObjectItemScreen

class ObjectItemScreenClass(private val objectName:String="unKnown"): Screen {
    @Composable
    override fun Content() {
        ObjectItemScreen(objectName=objectName,modifier = Modifier)
    }
}