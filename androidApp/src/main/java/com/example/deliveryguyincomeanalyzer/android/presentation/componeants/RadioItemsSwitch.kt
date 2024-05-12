package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectType
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.utilMapers.getSumObjectIntType

@Composable
fun RadioItemsSwitch(item1:String, onItem1:()->Unit, pickedItem:Int,shiftType:String,
                     item2:String, onItem2:()->Unit,
                     item3:String="no", onItem3:(Int)->Unit={},
                       ) {

    Row(modifier = Modifier
        .clip(MaterialTheme.shapes.extraLarge)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(9.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ) {
        Text(text = item1, modifier = Modifier
            .clickable { onItem1() }
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                if (pickedItem == 1)
                    MaterialTheme.colorScheme.primary
                else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
            .padding(18.dp),
            color = MaterialTheme.colorScheme.onPrimary)


        Spacer(modifier = Modifier.width(12.dp))

        Text(text = item2, modifier = Modifier
            .clickable { onItem2() }
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                if (pickedItem == 2)
                    MaterialTheme.colorScheme.primary
                else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
            .padding(18.dp),
            color = MaterialTheme.colorScheme.onPrimary)

        if (item3 != "no") {
            var shiftMenu by remember { mutableStateOf(false) }
            Spacer(modifier = Modifier.width(12.dp))

            var theShiftHeader = item3
            if(pickedItem>=3){
                theShiftHeader = shiftType
            }else
                theShiftHeader = item3


            Column {
                Text(text = theShiftHeader, modifier = Modifier
                    .clickable { shiftMenu = !shiftMenu }
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        if (pickedItem >= 3)
                            MaterialTheme.colorScheme.primary
                        else {
                            MaterialTheme.colorScheme.primaryContainer
                        }
                    )
                    .padding(18.dp),
                    color = MaterialTheme.colorScheme.onPrimary)

                DropdownMenu(
                    expanded = shiftMenu,
                    onDismissRequest = { shiftMenu = false },
                    modifier = Modifier
                ) {
                    val shiftTypes = listOf("Morning", "Noon", "Night")
                    for (i in shiftTypes) {
                        DropdownMenuItem(text = {
                            Text(text = i)
                        }, onClick = {
                            shiftMenu = false
                            onItem3((getSumObjectIntType(i)))
                        })
                    }
                }
            }
        }
    }

}


