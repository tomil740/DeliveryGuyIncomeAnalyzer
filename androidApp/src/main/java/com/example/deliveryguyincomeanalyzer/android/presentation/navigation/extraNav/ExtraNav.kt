package com.example.deliveryguyincomeanalyzer.android.presentation.navigation.extraNav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.ObjectItemScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.OverViewScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.PlatformBuilderScreenClass

/*
this function will be used in every screen as an floating top extra navigation menu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraNavigationIcon(navigator: Navigator  , modifier: Modifier=Modifier) {

    var isExpended by remember { mutableStateOf(false) }
    Box(modifier){
        ExposedDropdownMenuBox(
            expanded = isExpended,
            onExpandedChange = { isExpended = !isExpended },
            Modifier
        )
        {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null
                , modifier = Modifier .menuAnchor()
                    .size(32.dp), tint = MaterialTheme.colorScheme.onPrimary
            )

            ExposedDropdownMenu(
                expanded = isExpended,
                onDismissRequest = { isExpended = false },
                modifier = Modifier
                    .requiredSize(150.dp)
            ) {
                DropdownMenuItem(text = {
                    Text(
                        text = "Archive",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    navigator?.push(ObjectItemScreenClass(objectName = "Archive"))
                    isExpended = false
                })
                DropdownMenuItem(text = {
                    Text(
                        text = "PlatformsEditor",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    navigator?.push(PlatformBuilderScreenClass())
                    isExpended = false
                })
                DropdownMenuItem(text = {
                    Text(
                        text = "objects comparesor",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    navigator?.push(ObjectItemScreenClass(objectName = "Object comparesor"))
                    isExpended = false
                })
                DropdownMenuItem(text = {
                    Text(
                        text = "Platforms comparesor",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    navigator?.push(ObjectItemScreenClass(objectName = "Object comparesor"))
                    isExpended = false
                })


            }
        }


    }

}