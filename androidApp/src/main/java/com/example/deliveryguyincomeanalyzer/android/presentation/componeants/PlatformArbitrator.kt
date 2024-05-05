package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.domain.model.uiSubModels.WorkingPlatformOptionMenuItem
import me.saket.cascade.CascadeDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformArbitrator(showHeader:Boolean=false, pickedPlatform:String="", onPlatformPick:(String)->Unit={}, textColor:Color = MaterialTheme.colorScheme.onPrimary, navToBuild:()->Unit, context:String = "",
                       isBuilderWorkSession:Boolean = false, theLst:List<WorkingPlatformOptionMenuItem> = listOf(), onGeneralStat:()->Unit={}, onMyStat:()->Unit={}
) {
    var isExpended by remember { mutableStateOf(false) }

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var isNewPlatform by remember { mutableStateOf(false) }

    var theItem =
     " : $pickedPlatform"


        if(theItem == " : Add +"){
            if(context == "Platform Builder")
                isNewPlatform = true
            else{
                isNewPlatform = false
                navToBuild()
            }
        }
    else{
        isNewPlatform =false
        }

    if (isExpended) {
        arrowIcon.value = Icons.Default.KeyboardArrowUp
    } else {
        arrowIcon.value = Icons.Default.ArrowDropDown
    }


    Box {


        Row {
            if (showHeader) {
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
            }
            if(!isNewPlatform) {
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
                    CascadeDropdownMenu(
                        expanded = isExpended,
                        onDismissRequest = { isExpended = false },
                        modifier = Modifier
                    ) {

                        for (i in theLst) {
                            DropdownMenuItem(
                                text = { Text(text = i.platformName) },
                                children = {
                                    for (t in i.workingZones) {
                                        DropdownMenuItem(
                                            text = { Text(text = t) },
                                            onClick = {
                                                isExpended = false
                                                //theItem = "${i.platformName}-$t"
                                                onPlatformPick("${i.platformName}-$t")
                                            }
                                        )
                                    }
                                }
                            )
                        }
                        if (!isBuilderWorkSession) {
                            DropdownMenuItem(
                                text = { Text(text = "Any") },
                                onClick = {
                                    isExpended = false
                                    onPlatformPick("Any")
                                    //  theItem = "Any"
                                }
                            )
                        }
                    }
                }
            }
            if (isNewPlatform){
               OutlinedTextField(value = theItem, onValueChange = {theItem = it})
            }
        }
    }
}
@Preview
@Composable
fun preview() {
   // PlatformArbitrator(navToBuild = {})
}