package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.core.valuePresentation

@Composable
fun DeliveryLiveItem(theIndex:Int,theValue:Float,onDelete:()->Unit,modifier: Modifier = Modifier) {

    Row(modifier = modifier
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .border(2.dp, MaterialTheme.colorScheme.onSecondaryContainer)
        .padding(4.dp)
        , horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = theIndex.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.width(9.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(36.dp)
            )

            Text(
                text = valuePresentation(theValue),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.width(9.dp))

        Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier
            .size(28.dp)
            .clickable { onDelete() })






    }

}