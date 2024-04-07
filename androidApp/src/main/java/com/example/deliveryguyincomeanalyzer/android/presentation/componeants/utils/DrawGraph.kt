package com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils

import android.graphics.PointF
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/*
DrawGraph :
will implement the graph itSelf with the draw scope function according to the data sent to it
 */
@Composable
fun DrawGraph(verticalFrameSize:Float, examplePathData: List<Float>, areaMarkColor: Color = Color.Green, isMain:Boolean=false,
              showTotalIncome:Boolean,showAverage:Boolean,graphAverageValue:Float){


    Box(modifier = Modifier.padding(16.dp)
    ) {

        Spacer(
            modifier = Modifier
                .padding(8.dp)
                //.aspectRatio(5 / 2f)
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {

                        val resultList = mutableListOf<Float>()
                        for (i in examplePathData) {
                            resultList.add((verticalFrameSize) * i)
                        }
                        val path = generateSmothPath(resultList, size)

                        val fillPath = Path()
                        fillPath.addPath(path)
                        fillPath.lineTo(size.width, size.height)
                        fillPath.lineTo(0f, size.height)
                        fillPath.close()

                        //mark with color
                       drawPath(path, areaMarkColor, style = Stroke(2.dp.toPx()))

                        val brush =
                            Brush.verticalGradient(
                                if(showTotalIncome){
                                    listOf(
                                        if (isMain)
                                            areaMarkColor.copy(alpha = 1f)
                                        else {
                                            areaMarkColor.copy(alpha = 0.35f)
                                        },
                                        Color.Transparent
                                    )
                                }else{
                                    listOf(
                                        if (isMain)
                                            areaMarkColor.copy(alpha = 0.25f)
                                        else {
                                            areaMarkColor.copy(alpha = 0.15f)
                                        },
                                        Color.Transparent
                                    )
                                }
                            )

                        drawPath(fillPath, brush = brush, style = Fill)
                        if (showAverage)
                            drawLine(color= areaMarkColor, Offset(x=0f,y=graphAverageValue),Offset(x=size.width,y=graphAverageValue),
                                strokeWidth =7f)
                           // drawPath(averagePath,brush, style = Fill)

                    }
                }
        )
    }
}

fun generateSmothPath(data: List<Float>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size
    //that the constent X size to draw (represent a day)
    val weekWidth = size.width / numberEntries

    //for the Y one we will use the data from my list ...

    var currentX =0f
    var currentY =0f
    data.forEachIndexed { i, balance ->


        if (i == 0) {
            currentX = 0f
            currentY = size.height
            path.moveTo(0f, size.height)
        }
        // else {
        val balanceX = (i + 1) * weekWidth
        val balanceY = size.height - balance

        val controlPoint1 = PointF((balanceX + currentX) / 2f, currentY)
        val controlPoint2 = PointF((balanceX + currentX) / 2f, balanceY)


        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX,balanceY)
        currentX = balanceX
        currentY = balanceY


        //}
    }
    return path
}