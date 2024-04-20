package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.deliveryguyincomeanalyzer.android.presentation.core.TableRatio
import com.example.deliveryguyincomeanalyzer.android.presentation.core.getRatio
import java.nio.file.WatchEvent

/*
GenerateGraphTable :
*arguments :*
* columns : Int that will be generate according to the graph maximum value plus one in order to catch any unnatural values
* row : Int will be generate according to the list size
* timeStart : if the graph has some starting point (every shift item)

will draw a table with unit marks according to the state values sent to it to fit perfectly to our graphs, the marked values
will adjust to any change of the graphs on the table with the matched helper function in the "tableRatio" class

 */
@Composable
fun GraphTable(columnsTable: TableRatio, rows:Int, backgroundColor: Color, barsColors: Color,
               setVerticalFrameSize:(Float)-> Unit, verticalFrameSize:Float, startTime:Int,
               onStartTime:(Int)->Unit, onEndTime:(Int)->Unit, startTimeOptionLst:List<Int>, endTimeOptionLst:List<Int>,
               onShowSettings:()->Unit) {

    val density = LocalDensity.current
    var columnSize by remember { mutableStateOf(2.dp) }
    var rowSize by remember { mutableStateOf(2.5.dp) }

    var tableVerticalFrame:Float by remember { mutableFloatStateOf(verticalFrameSize) }


    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
        Canvas(modifier = Modifier
            .padding(8.dp)
            // .aspectRatio(columnSize / rowSize)
            .fillMaxSize()
            .onGloballyPositioned {
                columnSize = with(density) {
                    it.size.height.toDp()
                }
                rowSize = with(density) {
                    it.size.width.toDp()
                }
            }
        ) {
            val barWithPx = 1.dp.toPx()
            drawRect(barsColors, style = Stroke(barWithPx))
            val horizontalFrameSize = size.width / rows.toFloat()
            repeat(rows) {
                val startX = horizontalFrameSize * (it)
                drawLine(barsColors, start = Offset(startX, 0f), end = Offset(startX, size.height))
            }

            //size.height / columns)
            tableVerticalFrame = size.height/ columnsTable.tableColumns
            setVerticalFrameSize(tableVerticalFrame/columnsTable.columnsRatio)
            repeat(columnsTable.tableColumns) {
                val startY = tableVerticalFrame * (it)
                drawLine(barsColors, start = Offset(0f, startY), end = Offset(size.width, startY))
            }
        }
        //the marking method ...

        val p = 6f*1.dp
        for (i in 0..rows) {
            var theMark = startTime+i
            if(theMark > 23)
                theMark-=24
            var theText = "${(theMark)}"
            if(theText.length < 2)
                theText ="0$theText"

            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = rowSize * (1f / rows) * (i) + p, y = 4.dp),
                //
            ) {


                if (i == 0) {
                    var startTimeMenu by remember { mutableStateOf(false) }

                    Box(modifier = Modifier.clickable {
                        startTimeMenu = !startTimeMenu
                    }) {

                        Text(text = ".", style = MaterialTheme.typography.displaySmall)

                        Text(
                            text = theText,
                            Modifier
                                .align(Alignment.BottomStart)
                                .offset(y = 17.dp, x = -7.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Blue
                        )
                    }

                    DropdownMenu(
                        expanded = startTimeMenu,
                        onDismissRequest = { startTimeMenu = false },
                        modifier = Modifier
                            .width(width = 75.dp),
                        offset = DpOffset(y= (30).dp,x=0.dp)
                    ) {

                        for (f in startTimeOptionLst){
                            var theOption = f
                            if (theOption> 23)
                                theOption-=24
                            DropdownMenuItem(text = {
                                Text(text = "$theOption:00")
                            }, onClick = {
                                startTimeMenu = false
                                onStartTime(f)
                            })
                        }
                    }
                }

                if (i == rows ) {
                    var endTimeMenu by remember { mutableStateOf(false) }

                    Box(modifier = Modifier.clickable {
                        endTimeMenu = !endTimeMenu
                    }) {
                        Text(text = ".", style = MaterialTheme.typography.displaySmall)

                        Text(
                            text = theText,
                            Modifier
                                .align(Alignment.BottomStart)
                                .offset(y = 17.dp, x = -7.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Blue
                        )
                    }
                    DropdownMenu(
                        expanded = endTimeMenu,
                        onDismissRequest = { endTimeMenu = false },
                        modifier = Modifier
                            .width(width = 75.dp),
                        offset = DpOffset(y= (30).dp,x=0.dp)
                    ) {

                        for (f in endTimeOptionLst){
                            var theOption = f
                            if (theOption> 23)
                                theOption-=24
                            DropdownMenuItem(text = {
                                Text(text = "$theOption:00")
                            }, onClick = {
                                endTimeMenu = false
                                onEndTime(f)
                            })
                        }
                    }
                } else if (i!=rows && i !=0) {

                    Text(text = ".", style = MaterialTheme.typography.titleLarge)

                    Text(
                        text = theText,
                        Modifier
                            .align(Alignment.BottomStart)
                            .offset(y = 12.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
//columns will genarate from the graph and will be the maximum value + 10
        for (i in 0..columnsTable.tableColumns) {
            var theText = columnsTable.getTextUnit(i)

            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .offset(y = columnSize * (1f / columnsTable.tableColumns) * (i) - 12.5.dp, x = 4.dp),
                //
            ) {

                Text(text =".",style = MaterialTheme.typography.titleLarge)

                Text(
                    text = theText,
                    Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = -16.dp)
                    ,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        //graph settings icon
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .size(44.dp),
        ) {

            Icon(imageVector =Icons.Default.Settings , contentDescription =null , modifier = Modifier.clickable { onShowSettings() })


        }


    }
}