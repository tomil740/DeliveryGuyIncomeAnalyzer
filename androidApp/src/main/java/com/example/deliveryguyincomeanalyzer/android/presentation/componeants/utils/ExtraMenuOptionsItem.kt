package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExtraMenuOptionsItem(menuItems: List<String>, onItemClick:(String)->Unit, itemBackground: Color
                         , itemColor: Color) {

    val deffault = listOf<String>("1","5","10","15","20")
    val theMenu = mutableListOf<String>()

    for (i in menuItems){
        theMenu.add(i)
    }

    if(menuItems.size < 5){
        var counter  = 0
        while (theMenu.size < 5){
            theMenu.add(deffault.get(counter))
            counter++
        }
    }

    var pickedItem by remember{ mutableStateOf("0") }

    Column(modifier = Modifier.padding(bottom = 14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.clickable {onItemClick(theMenu.get(0))}) {
                BubbleTextItem(theMenu.get(0), itemBackground = itemBackground, itemColor = itemColor)
            }
            Box(modifier = Modifier.clickable {onItemClick(theMenu.get(1))}) {
                BubbleTextItem(theMenu.get(1), itemBackground = itemBackground, itemColor =itemColor)
            }
            Box(modifier = Modifier.clickable {onItemClick(theMenu.get(2))}) {
                BubbleTextItem(theMenu.get(2), itemBackground = itemBackground, itemColor = itemColor)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.clickable {onItemClick(theMenu.get(3))}) {
                BubbleTextItem(theMenu.get(3), itemBackground = itemBackground, itemColor =itemColor)
            }
            Box(modifier = Modifier.clickable {onItemClick(theMenu.get(4))}) {
                BubbleTextItem(theMenu.get(4), itemBackground = itemBackground, itemColor = itemColor)
            }
        }
    }

}