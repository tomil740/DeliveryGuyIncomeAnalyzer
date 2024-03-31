package com.example.deliveryguyincomeanalyzer.android.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.MainObjectHeaderItem
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.PlatformArbitrator
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.SmallObjectItem

@Composable
fun OverViewScreen(modifier:Modifier) {

    Box(modifier = modifier
        .fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MainObjectHeaderItem()

            Text(
                text = "Recent work :",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            LazyRow {
                items(7) {
                    Box(Modifier.padding(start = 4.dp, end = 4.dp)) {
                        SmallObjectItem(
                            objectHeader = "Sunday",
                            hoursComparable = 8f,
                            hoursValue = 7f,
                            income = 750f
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                PlatformArbitrator(textColor = MaterialTheme.colorScheme.primary)
            }
        }
    }
}