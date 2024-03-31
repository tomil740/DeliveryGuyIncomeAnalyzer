package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import android.view.MenuItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformArbitrator(textColor:Color = MaterialTheme.colorScheme.onPrimary) {
    val theLst = mutableListOf<String>("Wolt","dominos","Mcdonalds","KFC","תן ביס")

    var isExpended by remember { mutableStateOf(false) }

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var theItem by remember { mutableStateOf(" : Wolt") }

    if (isExpended) {
        arrowIcon.value = Icons.Default.KeyboardArrowUp
    } else {
        arrowIcon.value = Icons.Default.ArrowDropDown
    }


    Box {


        Row {
            Column() {
                Text(
                    text = "Working",
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
                Text(
                    text = "platform",
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
            }
            ExposedDropdownMenuBox(
                expanded = isExpended,
                onExpandedChange = { isExpended = !isExpended },
            )
            {
                    Row(
                        modifier = Modifier
                            .menuAnchor()
                    ) {

                        Text(
                            text = theItem,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .offset(y = -2.dp),
                            color = textColor
                        )

                        Icon(
                            imageVector = arrowIcon.value,
                            contentDescription = null,
                            modifier = Modifier.offset(y = 10.dp),
                            tint = textColor
                        )
                    }

                        ExposedDropdownMenu(
                            expanded = isExpended,
                            onDismissRequest = { isExpended = false },
                            modifier = Modifier
                                .height(150.dp)
                        ) {
                            theLst.forEach {
                                DropdownMenuItem(text = {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }, onClick = {
                                    theItem = " : $it"
                                    isExpended = false
                                })

                            }
                    }
            }
        }
    }
}
@Preview
@Composable
fun preview() {
    PlatformArbitrator()
}