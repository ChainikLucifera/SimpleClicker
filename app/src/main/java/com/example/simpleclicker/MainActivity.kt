package com.example.simpleclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpleclicker.viewmodels.ClickerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: ClickerViewModel = viewModel()
            Main(viewModel)
        }
    }
}

@Composable
fun Main(viewModel: ClickerViewModel = viewModel()){
    //собираем статы из Flow в State чтобы использовать в UI
    val stats = viewModel.state.collectAsStateWithLifecycle()

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(text = "You have the value ${stats.value.clicks} of clicks!")

        Spacer(Modifier.height(50.dp))

        Button({viewModel.addClick()}) {
            Text("Click Me")
        }
        Row()
        {
            Button({viewModel.addPower()}) {
                Text("Upgrade power")
            }

            Spacer(Modifier.width(50.dp))

            Button({viewModel.addClick()}) {
                Text("Click Me2")
            }
        }
    }
}
