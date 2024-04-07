package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.DeclareLiveBuilderScreen

class DeclareBuilderScreenClass: Screen {
    @Composable
    override fun Content() {
        DeclareLiveBuilderScreen(modifier = Modifier)

    }
}
