package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.ArchiveItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.BooleanItemsSwitch
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem

@Composable
fun ObjectItemScreen(modifier: Modifier = Modifier) {

    //to use the floating expandet item arrow mark , we must implement it in box layout

    var isItem1 by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .offset( y = -20.dp)
                    .size(58.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }) { paddingVal->

        LazyColumn(modifier.padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                MainObjectHeaderItem(modifier = modifier)
            }

            item {
                Spacer(modifier = Modifier.height(18.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    BooleanItemsSwitch(
                        item1 = "Statistics",
                        onItem1 = { isItem1 = true },
                        item2 = "Archive",
                        onItem2 = { isItem1 = false },
                        isItem1
                    )
                }
            }

            items(20) {
                ArchiveItem(
                    objectName = "January",
                    barSizeParam = 8000f,
                    barValueParam = 6655f,
                    subSizeParam = 215f,
                    subValueParam = 144.8f,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }
        }
    }
}