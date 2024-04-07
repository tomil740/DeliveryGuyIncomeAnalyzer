package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.TextDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftBuilderItem(
    shiftHeader:String = "Morning Shift :",
    backgroundColor:Color=MaterialTheme.colorScheme.primaryContainer,
    timeItemBackground:Color=MaterialTheme.colorScheme.primary,
    timeItemTextColor:Color= MaterialTheme.colorScheme.onPrimary,
    textColor:Color= MaterialTheme.colorScheme.onPrimaryContainer,
    shiftOptions:List<String>,
    startTime:String="08:00",
    endTime:String="14:00",
    onStart:(String)->Unit={},
    onEnd:(String)->Unit={},
    modifier: Modifier=Modifier
) {

    Box(modifier = Modifier
        .clip(MaterialTheme.shapes.extraLarge)
        .background(backgroundColor)) {
        Column(
            modifier = modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = shiftHeader,
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                modifier = Modifier.padding(start = 6.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextDropDownMenu(currentVal = startTime,
                    optionsLst = shiftOptions, textColor = timeItemTextColor,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(timeItemBackground)
                        .padding(6.dp)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward, contentDescription = null,
                    Modifier
                        .size(36.dp)
                        .offset(y = -6.dp)
                        .clip(MaterialTheme.shapes.small),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )

                TextDropDownMenu(currentVal = endTime,
                    optionsLst = shiftOptions, textColor = timeItemTextColor,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(timeItemBackground)
                        .padding(6.dp)
                )

            }
        }
    }
}

