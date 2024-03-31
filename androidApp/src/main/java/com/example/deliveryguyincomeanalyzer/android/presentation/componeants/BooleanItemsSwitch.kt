package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BooleanItemsSwitch(item1:String,onItem1:()->Unit,
                       item2:String,onItem2:()->Unit,isItem1:Boolean) {

    Row(modifier = Modifier
        .clip(MaterialTheme.shapes.extraLarge)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(9.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ) {
        Text(text = item1, modifier = Modifier
            .clickable { onItem1() }
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                if(isItem1)
                  MaterialTheme.colorScheme.primary
            else {
                    MaterialTheme.colorScheme.primaryContainer
                })
            .padding(18.dp),
            color = MaterialTheme.colorScheme.onPrimary)

        
        Spacer(modifier = Modifier.width(12.dp))

        Text(text = item2, modifier = Modifier
            .clickable { onItem2() }
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                if(!isItem1)
                    MaterialTheme.colorScheme.primary
                else {
                    MaterialTheme.colorScheme.primaryContainer
                })
            .padding(18.dp),
            color = MaterialTheme.colorScheme.onPrimary)
    }
}


