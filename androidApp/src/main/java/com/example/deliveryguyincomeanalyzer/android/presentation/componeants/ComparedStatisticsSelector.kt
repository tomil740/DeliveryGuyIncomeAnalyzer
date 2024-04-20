package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import me.saket.cascade.CascadeDropdownMenu

@Composable
fun ComparedStatisticsSelector(onCloseArchiveMenu:()->Unit={},onOpenArchiveMenu:()->Unit={},comparableName:String="April 2024, Any",textColor: Color=MaterialTheme.colorScheme.onPrimary) {
    val firstMenu = listOf<String>("MyStatistics","GeneralStatistics")
    val platformsLst = listOf<String>("All","Wolt", "dominos", "Mcdonalds", "KFC", "תן ביס")

    var isExpended by remember { mutableStateOf(false) }

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var theItem by remember { mutableStateOf(comparableName) }

    LaunchedEffect(key1 = comparableName) {
        theItem=comparableName
    }

    if (isExpended) {
        arrowIcon.value = Icons.Default.KeyboardArrowUp
    } else {
        arrowIcon.value = Icons.Default.ArrowDropDown
    }

    Box {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Compared",
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
                Text(
                    text = "Object",
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
            }
            Row(modifier = Modifier.clickable { isExpended = !isExpended }) {
                Text(
                    text = ":$theItem",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .offset(y = -2.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Icon(
                    imageVector = arrowIcon.value,
                    contentDescription = null,
                    modifier = Modifier.offset(y = 10.dp),
                    tint = textColor
                )
            }
        }
        CascadeDropdownMenu(
            expanded = isExpended,
            onDismissRequest = { isExpended = false },
            modifier = Modifier
        ) {

            for (i in firstMenu) {
                DropdownMenuItem(
                    text = { Text(text = i) },
                    children = {
                        for (t in platformsLst) {
                            DropdownMenuItem(
                                text = { Text(text = t) },
                                onClick = {
                                    onCloseArchiveMenu()
                                    isExpended = false
                                    theItem = "$i->$t"
                                }
                            )
                        }
                    }
                )
            }
            DropdownMenuItem(text = { Text(text = "Archive") }
                , onClick = {
                    isExpended = false
                    onOpenArchiveMenu()
                })
        }
    }
}