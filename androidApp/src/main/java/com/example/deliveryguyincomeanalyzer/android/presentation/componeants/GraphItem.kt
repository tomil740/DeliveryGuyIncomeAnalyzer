package com.example.deliveryguyincomeanalyzer.android.presentation.componeants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.DrawGraph
import com.example.deliveryguyincomeanalyzer.android.presentation.componeants.utils.GraphTable
import com.example.deliveryguyincomeanalyzer.android.presentation.core.TableRatio
import com.example.deliveryguyincomeanalyzer.android.presentation.core.getRatio
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.GraphState

/*
GraphItem :

*arguments*
this compose function will get as parameters up to 3 graphs (in a practical way those are float list represent work per hour in this case work per index)
*the values of the list will be by default in ils units...*,
colors to each graph and to the table and modifier for general size and etc...

*general target*
The function will mange all of the sub components of the item for example graphs or drop down menus to be all with matched states values and positioned as well
all as they should be , that will be done with the states manged in it and the matched graphs data class and her functions (explained widely above)

in practice :
As described this function will create the most suitable table and aspects ratios according to the graph sent to it while be flexable to
get any screen size , that will be mange with one state that will define the vertical size of one bar , by that all of the table componeants
stay sync to each other and to the screen size
*the table is draw from 0.dp at the top to the matched canvas size(define at the table function and returned accordingly to the state)*
In order to position point on the graph table we will take the full size (which is the zero point) and subtract the matched point vertically
 */
@Composable
fun GraphItem(perHourValueGraphState: GraphState, perHourComparableGraphState: GraphState, perDeliverValueGraphState: GraphState, perDeliverComparableGraphState: GraphState,
              modifier: Modifier = Modifier) {

    var showSettings by remember { mutableStateOf(false) }
    var isPerHour by remember { mutableStateOf(true) }
    var showTotalIncome by remember { mutableStateOf(false) }
    var showAverage by remember { mutableStateOf(false) }

    //just for demonstration //
    var graphHeader = "Hour"
    if (isPerHour)
        graphHeader = "Hour"
    else{
        graphHeader = "Delivery"
    }

    //those states are for each graph and will keep the UI up to date and interactive
    var graphValueState: GraphState by remember { mutableStateOf(perHourValueGraphState) }
    var graphComparableState by remember { mutableStateOf(perHourComparableGraphState) }
    var showValueGraph by remember { mutableStateOf(true) }
    var showComparableGraph by remember { mutableStateOf(true) }


    //the launched effect is responsable to the flexible feature of our graph , to be size according to the picked graphs that we present on it
    //we will update all of the graphs and the table in every change on any graph to keep all scale as well as poseeable


    var columnsTable:TableRatio by remember { mutableStateOf(getRatio( graphValueState.getColumnSize()))}


    LaunchedEffect(key1 = isPerHour) {

        if (isPerHour) {
            graphValueState = perHourValueGraphState
            graphComparableState = perHourComparableGraphState
        }
        if (!isPerHour) {
            graphValueState = perDeliverValueGraphState
            graphComparableState = perDeliverComparableGraphState
        }
    }

    LaunchedEffect(key1 = graphValueState,key2 = graphComparableState) {
        //the changes of the graph table will come only from the value graph and the comparable will adapted accordingly here
        graphComparableState =
            graphComparableState.UpdateGraphData(graphValueState.startTime, graphValueState.endTime)
        if (graphComparableState.getColumnSize() > graphValueState.getColumnSize()){
            columnsTable = getRatio(graphComparableState.getColumnSize())
        }
        else{
            columnsTable = getRatio( graphValueState.getColumnSize())
        }



    }


    //with this state we make shure all of the graph are in sync by the size attribute , needed because we will not alwase set a fixed size
    //to our function ...
    var verticalFrameSize by remember { mutableStateOf(graphValueState.getColumnSize()) }


    Column {

        Row(modifier = Modifier
            .padding(top = 24.dp, start = 14.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Income per ",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary)

            Text(
                text = graphHeader,
                style = MaterialTheme.typography.displaySmall.copy(fontFamily = FontFamily.Serif),
                color = MaterialTheme.colorScheme.primary,
            modifier=Modifier.clickable { isPerHour=!isPerHour })

        }

        //at this box we will position one on top each other all of the graph elements
        Box(modifier = modifier, contentAlignment = Alignment.Center) {

            //the graph table function , will draw according to the matched sates and update accordingly
            GraphTable(
                columnsTable = columnsTable,
                rows = graphValueState.dataLst.size,
                backgroundColor = Color.Gray,
                startTime = graphValueState.startTime,
                barsColors = Color.Black,
                setVerticalFrameSize =
                { verticalFrameSize = it },
                verticalFrameSize = verticalFrameSize,
                onEndTime = { graphValueState = graphValueState.UpdateGraphData(endTime1 = it) },
                onStartTime = {
                    graphValueState = graphValueState.UpdateGraphData(startTime1 = it)
                },
                startTimeOptionLst = graphValueState.getStartOptionsLst(),
                endTimeOptionLst = graphValueState.getEndOptionsLst(),
                onShowSettings = {showSettings = !showSettings}
            )
            //generate the graph itSelf according to the row number passed to the table above , and the list of Data to be present int the table
            //*importent the data list must be in the range of the coulms value (could be uneturial )  *
            if (showComparableGraph) {
                DrawGraph(
                    verticalFrameSize = verticalFrameSize,
                    examplePathData = graphComparableState.dataLst,
                    areaMarkColor = Color.Red,false,showTotalIncome,showAverage, graphAverageValue = ((columnsTable.tableColumns*columnsTable.columnsRatio)-graphComparableState.getAverage())*verticalFrameSize
                )
            }

            if(showValueGraph) {
                DrawGraph(
                    verticalFrameSize = verticalFrameSize,
                    examplePathData = graphValueState.dataLst,
                    areaMarkColor = Color.Green,isMain = true,showTotalIncome,showAverage,graphAverageValue = ((columnsTable.tableColumns*columnsTable.columnsRatio)-graphValueState.getAverage())*verticalFrameSize
                )
            }


        }
    }
    //those are the on/off button of each graph with matched states and colors
    if(showSettings) {
        Row(
            modifier = Modifier
                .padding(start = 45.dp, end = 45.dp, top = 16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { showComparableGraph = !showComparableGraph }, colors =
                if (showComparableGraph)
                    ButtonDefaults.outlinedButtonColors()
                        .copy(containerColor = Color.Red, contentColor = Color.White)
                else {
                    ButtonDefaults.outlinedButtonColors()
                        .copy(containerColor = Color.White, contentColor = Color.Red)
                }
            ) {
                Text(text = "Comparable")

            }

            OutlinedButton(
                onClick = { showValueGraph = !showValueGraph }, colors =
                if (showValueGraph)
                    ButtonDefaults.outlinedButtonColors()
                        .copy(containerColor = Color.Green, contentColor = Color.White)
                else {
                    ButtonDefaults.outlinedButtonColors()
                        .copy(containerColor = Color.White, contentColor = Color.Green)
                }
            ) {
                Text(text = "Value")

            }
        }
    }


    Row(modifier= Modifier
        .padding(start = 45.dp, end = 45.dp, top = 16.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        BasicDataSumBar(barVal = graphValueState.getIncomeSum(), graphComparableState.getIncomeSum(), Color.Red,Color.Green,"Total income",modifier=Modifier.clickable { showTotalIncome=!showTotalIncome })

    }
    Row(modifier= Modifier
        .padding(start = 45.dp, end = 45.dp, top = 16.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

        BasicDataSumBar(barVal = graphValueState.getAverage(), graphComparableState.getAverage(), Color.Red, Color.Green, "Average",modifier=Modifier.clickable { showAverage=!showAverage })
    }
}