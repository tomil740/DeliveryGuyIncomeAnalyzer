package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
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
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import me.saket.cascade.CascadeDropdownMenu

@Composable
fun ComparedStatisticsSelector(onCloseArchiveMenu:()->Unit={}, onOpenArchiveMenu:()->Unit={}, pickedPlatformComparable:String="April 2024, Any",
                               sumObjectSourceType: SumObjectSourceType,
                               platformsMenu1:List<WorkingPlatformOptionMenuItem> = listOf(), platformsMenu2:List<WorkingPlatformOptionMenuItem> = listOf(),
                               onMyStatItemPick:(String)->Unit, onGeneralStatPick:(String)->Unit, textColor: Color=MaterialTheme.colorScheme.onPrimary) {
    val firstMenu = listOf<String>("My-Statistics","General-Statistics")

    var isExpended by remember { mutableStateOf(false) }

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var theItem =
        " : $pickedPlatformComparable"

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
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = sumObjectSourceType.name ,
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor,
                )
                Row(modifier = Modifier.clickable { isExpended = !isExpended }) {
                    Text(
                        text = theItem,
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
                        var thePlatformsLst = platformsMenu1
                        if(i ==firstMenu[0])
                            thePlatformsLst=platformsMenu1.plus(platformsMenu2)
                        for (t in thePlatformsLst) {
                            DropdownMenuItem(
                                text = { Text(text = t.platformName) },
                                children = {
                                    for (j in t.workingZones) {
                                        DropdownMenuItem(
                                            text = { Text(text = j) },
                                            onClick = {
                                                isExpended = false
                                                if(i == firstMenu[0]){
                                                    onMyStatItemPick("${t.platformName}-$j")
                                                }else{
                                                    onGeneralStatPick("${t.platformName}-$j")
                                                }
                                                theItem = "${t.platformName}->$j"
                                            }
                                        )
                                    }
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text(text = "Any") },
                            onClick = {
                                isExpended = false
                                if(i == firstMenu[0]){
                                    onMyStatItemPick("Any")
                                }else{
                                    onGeneralStatPick("Any")
                                }
                                 theItem = "Any"
                            }
                        )
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