package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType
import me.saket.cascade.CascadeDropdownMenu

@Composable
fun SumObjectItemSelector(isValue:Boolean = true, onCloseArchiveMenu:()->Unit={}, onOpenArchiveMenu:()->Unit={},
                          sumObjName:String="April 2024, Any", sumObjectSourceType: SumObjectSourceType,sumObjectWorkingPlatform:String,
                          platformsMenu1:List<WorkingPlatformOptionMenuItem> = listOf(), platformsMenu2:List<WorkingPlatformOptionMenuItem> = listOf(),
                          onUserStatItemPick:(String)->Unit, onGeneralStatPick:(String)->Unit, onArchivePick:(String)->Unit,
                          textColor: Color=MaterialTheme.colorScheme.onPrimary) {
    val firstMenu = listOf<String>("My-Statistics","General-Statistics")

    var isExpended by remember { mutableStateOf(false) }

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var theItem = "$sumObjectSourceType,$sumObjectWorkingPlatform"

    if (isExpended) {
        arrowIcon.value = Icons.Default.KeyboardArrowUp
    } else {
        arrowIcon.value = Icons.Default.ArrowDropDown
    }

    var theAlignment =  Alignment.CenterHorizontally
    var fontSize = MaterialTheme.typography.titleSmall

    if(isValue){
        theAlignment = Alignment.Start
        fontSize = MaterialTheme.typography.titleLarge
    }

    Box() {
        Row {
            if(!isValue) {
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
            }else {
                //if its value archive
                theItem = if (sumObjectSourceType == SumObjectSourceType.Archive) {
                    "$sumObjName,$sumObjectWorkingPlatform"
                }
                //value but aint an archive typed
                else{
                    "${sumObjectSourceType.name},$sumObjectWorkingPlatform"
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = theAlignment) {
                //|||||||||||||||||This is the top text ,apply only when we work with the comparable ...
                //check if its not an value but archive
                var theTopHeader =  sumObjectSourceType.name
                if (sumObjectSourceType == SumObjectSourceType.Archive)
                    theTopHeader=sumObjName
                if(!isValue){
                Text(
                    text = theTopHeader ,
                    style = fontSize,
                    color = textColor,
                )
                }
                //|||||||||||||||||This is apply only when we work with the comparable ...

                Row(modifier = Modifier.clickable { isExpended = !isExpended }) {
                    var theText = theItem
                     if(!isValue)
                        theText = sumObjectWorkingPlatform
                    Text(
                        text = theText,
                        style = fontSize,
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
                                                    onUserStatItemPick("${t.platformName}-$j")
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
                                    onUserStatItemPick("Any")
                                }else{
                                    onGeneralStatPick("Any")
                                }
                                 theItem = "Any"
                            }
                        )
                    }
                )
            }
            if(isValue) {
                DropdownMenuItem(
                    text = {Text(text = "Archive")},
                    children = {
                        val thePlatformsLst=platformsMenu1.plus(platformsMenu2)
                        for (i in thePlatformsLst) {
                            DropdownMenuItem(text = { Text(text = i.platformName)}, children = {
                                for (j in i.workingZones) {
                                    DropdownMenuItem(text = { Text(text = j)}, onClick ={
                                        isExpended = false
                                        onArchivePick("${i.platformName}-$j")
                                    })
                                }
                            })
                        }
                        DropdownMenuItem(
                            text = { Text(text = "Any") },
                            onClick = {
                                isExpended = false
                                onArchivePick("Any")
                                theItem = "Any"
                            }
                        )
                    }
                )
            }else {
                DropdownMenuItem(text = { Text(text = "Archive") }, onClick = {
                    isExpended = false
                    onOpenArchiveMenu()
                })
            }
        }
    }
}