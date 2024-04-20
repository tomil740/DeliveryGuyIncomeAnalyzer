package com.example.deliveryguyincomeanalyzer.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.example.deliveryguyincomeanalyzer.android.presentation.navigation.screens.OverViewScreenClass
import com.example.deliveryguyincomeanalyzer.android.presentation.screens.DeclareLiveBuilderScreen
import com.example.deliveryguyincomeanalyzer.android.theme.MyApplicationTheme
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetDeclareDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetDeclareShifts
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderStatesAndEvents
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderViewmodel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator(screen = OverViewScreenClass())


                }

            }
        }
    }

    @Composable
    fun GreetingView(text: String) {
        Text(text = text)
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MyApplicationTheme {
            GreetingView("Hello, Android!")
        }
    }
}
