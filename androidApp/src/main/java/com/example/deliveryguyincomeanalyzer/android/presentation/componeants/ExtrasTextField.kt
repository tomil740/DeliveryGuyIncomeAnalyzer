package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.ExtraMenuOptionsItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtrasTextField(commonExras: List<String>) {

    var theExtra by remember {
        mutableStateOf("")
    }
    //the state of the drop dwon opteion menu
    var expanded by remember {
        mutableStateOf(false)
    }
    //this is an special object of the material library to be interactive according to the text field type from the user
    //in general all this object does is active the like an click on every interaction with the text field a
    val interactionSource = remember {
        MutableInteractionSource()
    }
    //the Actuall ui textField
    Column(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        //the header of the text field
        Text(
            modifier = Modifier
                .padding(start = 3.dp, bottom = 2.dp)
                .fillMaxWidth(),
            text = "Extras",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )

        //the actual UI item itself , will be consist from the text field itSelf and the drop down option menu

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column() {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                if (theExtra == "")
                                    theExtra = "0"
                                theExtra = (theExtra.toInt() + 1).toString()
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                if (theExtra == "")
                                    theExtra = "0"
                                theExtra = (theExtra.toInt() - 1).toString()
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.5f) ,
                    value = theExtra,
                    onValueChange = {
                        if(it.length < 7)
                           theExtra = it
                        expanded = true
                    },
                    prefix={ Text(text = "   $", color = MaterialTheme.colorScheme.onSecondaryContainer)},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        cursorColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(36.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.extraLarge
                )

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                        , tint = MaterialTheme.colorScheme.primary

                    )
                }

            }
            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    shape = RoundedCornerShape(45.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (commonExras.isNotEmpty() && theExtra != "") {
                        val a = (commonExras.filter {
                            it.contains(theExtra)
                        }.sorted())
                        val new = mutableListOf<String>()
                        new.add((theExtra.toInt() + 2).toString())
                        new.add((theExtra.toInt() - 2).toString())
                        for (i in a) {
                            new.add(i)
                        }
                        ExtraMenuOptionsItem(new, onItemClick = { theExtra = it
                            expanded = false}, itemBackground = MaterialTheme.colorScheme.secondary, itemColor = MaterialTheme.colorScheme.onSecondary)
                    } else {
                        ExtraMenuOptionsItem(
                            listOf("5", "10", "1", "15", "20"),
                            onItemClick = { theExtra = it
                            expanded = false}, itemBackground = MaterialTheme.colorScheme.secondary,MaterialTheme.colorScheme.onSecondary)
                    }

                }

            }
        }
    }
}


