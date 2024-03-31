package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
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
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.CircleProgressItem

@Composable
fun ExpandedDataItem(isExpandet:Boolean, isDefaultParam:Boolean=true,
                     barColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
                     valueColor: Color = MaterialTheme.colorScheme.secondaryContainer,
                     textColor:Color =MaterialTheme.colorScheme.onPrimary,) {

    var isDefault by remember { mutableStateOf(isDefaultParam) }

    AnimatedVisibility(isExpandet) {

        Box(modifier = Modifier.height(200.dp).clickable { isDefault=!isDefault }) {

            AnimatedContent(targetState = isDefault, label = "",
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(3000)
                    ) togetherWith fadeOut(animationSpec = tween(3000))
                },) { targetState ->
                when (targetState) {
                    true -> {
                        Row(Modifier.padding(16.dp)) {
                            CircleProgressItem(
                                valueHeader = "Per Hour",
                                barSize = 205f,
                                barValue = 180f,
                                barColor=barColor,
                                valueColor=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                            )
                        }
                    }

                    false -> {
                        Row(Modifier.padding(16.dp)) {
                            CircleProgressItem(
                                valueHeader = "Per Hour",
                                barSize = 205f,
                                barValue = 180f,
                                barColor=barColor,
                                valueColor=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                                    .weight(1f)
                            )

                            Spacer(modifier = Modifier.width(32.dp))

                            CircleProgressItem(
                                valueHeader = "Per Delivery",
                                barSize = 200f,
                                barValue = 19f,
                                barColor=barColor,
                                valueColor=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                                    .weight(1f)
                            )

                        }
                    }
                }
            }
        }
    }
}
