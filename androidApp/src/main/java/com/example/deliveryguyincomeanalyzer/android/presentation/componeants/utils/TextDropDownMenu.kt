package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDropDownMenu(
    textColor:Color= MaterialTheme.colorScheme.onPrimary,
    optionsLst:List<String>,
    currentVal:String="null",
    onEnter:(String)->Unit={},
    modifier: Modifier = Modifier
) {

    val arrowIcon = remember { mutableStateOf(Icons.Default.ArrowDropDown) }

    var isExpended by remember { mutableStateOf(false) }


    if (isExpended) {
        arrowIcon.value = Icons.Default.KeyboardArrowUp
    } else {
        arrowIcon.value = Icons.Default.ArrowDropDown
    }

    ExposedDropdownMenuBox(
        expanded = isExpended,
        onExpandedChange = { isExpended = !isExpended },
        modifier
    )
    {
        Row(
            modifier = Modifier
                .menuAnchor()
        ) {

            Text(
                text = currentVal,
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
            optionsLst.forEach {
                DropdownMenuItem(text = {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    onEnter(it)
                    isExpended = false
                })

            }
        }
    }
}

@Composable
@Preview
fun TextDropDownMenuPrev() {
    TextDropDownMenu(
        optionsLst= listOf("option1","option1","option1","option1"),
        currentVal="null",
        onEnter={},
    )

}