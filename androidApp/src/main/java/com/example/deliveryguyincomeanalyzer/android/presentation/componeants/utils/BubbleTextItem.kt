package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BubbleTextItem(theText: String , isMain : Boolean= false,itemBackground: Color, itemColor: Color) {

    var theModifier : Modifier = Modifier.offset(y=10.dp)
    var height = 45.dp
    var width = 82.dp
    var textStyle = MaterialTheme.typography.titleSmall

    if(isMain){
        width = 92.dp
        height = 55.dp
        theModifier = Modifier
        textStyle = MaterialTheme.typography.titleLarge
    }

    Box(modifier = theModifier
        .size(width = width, height = height)
        .padding(all = 4.dp)
        .border(2.dp, itemBackground, RoundedCornerShape(16.dp))
        .background(itemBackground, shape = RoundedCornerShape(16.dp))) {

        Text(
            text = theText,
            style = textStyle,
            color = itemColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}