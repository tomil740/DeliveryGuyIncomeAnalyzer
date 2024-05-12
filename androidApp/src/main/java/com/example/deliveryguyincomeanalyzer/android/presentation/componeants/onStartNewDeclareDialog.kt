package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.DeclareBuilderScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.TypedBuilderScreenClass

@Composable
fun OnStartNewDeclareDialog(modifier: Modifier,navigator:Navigator) {

    Row(modifier = modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        RadioItemsSwitch(
            item1 = "TypedBuilder",
            onItem1 = { navigator.push(TypedBuilderScreenClass()) },
            pickedItem = -1,
            shiftType = "",
            item2 = "LiveBuilder",
            onItem2 = { navigator.push(DeclareBuilderScreenClass())})
    }

}