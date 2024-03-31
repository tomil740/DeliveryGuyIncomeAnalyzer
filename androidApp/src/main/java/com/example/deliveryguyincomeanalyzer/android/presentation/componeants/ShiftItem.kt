package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.TwoValuesProgressBar
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.UnitDisplay
import java.math.RoundingMode

@Composable
fun ShiftItem() {

    Column(modifier = Modifier
        .clip(MaterialTheme.shapes.medium)
        .fillMaxWidth(0.75f)
        .background(MaterialTheme.colorScheme.primary), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Morning Shift :",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "08 : 00",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(start = 20.dp, end = 20.dp)
            )

            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null,
                Modifier
                    .size(36.dp)
                    .offset(y = -6.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,)

            Text(
                text = "13 : 00",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(start = 20.dp, end = 20.dp)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        TwoValuesProgressBar(barVal = 7.1f, comparableVal = 8.1f, subBarVal = 700f, subComparableVal = 800f, isFullBar = false)

    }


}