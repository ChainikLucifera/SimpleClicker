package com.example.simpleclicker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleclicker.viewmodels.ClickerViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: ClickerViewModel = viewModel()
            val localContext = LocalContext.current
            val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(Unit){
                viewModel.showToastEvent.observe(lifecycleOwner){ message ->
                    Toast.makeText(localContext, "$message", Toast.LENGTH_SHORT).show()
                }
            }

            Main(viewModel)
        }

    }
}

@Composable
fun Main(viewModel: ClickerViewModel = viewModel()) {
    //собираем статы из Flow в State чтобы использовать в UI
    val stats = viewModel.clickerState.collectAsStateWithLifecycle()
    val upgradeState = viewModel.upgradeState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(text = "You have the value ${stats.value.clicks} of clicks!")

        Spacer(Modifier.height(50.dp))

        Button({ viewModel.addClick() }) {
            Text("Click Me")
        }
        Row()
        {
            LazyColumn(modifier = Modifier
                .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,) {
                items(viewModel.getPowerUpgradesNumber()){ index ->
                    Button(onClick = {viewModel.buttonPressed(index)}) {
                        Text("${upgradeState.value.currentCost[index]}")
                    }
                }
            }

            Column(modifier = Modifier
                .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,) {
                ScrollBoxes()
            }
//            Button({viewModel.addPower()}) {
//                Text("Upgrade power")
//            }

//            Spacer(Modifier.width(50.dp))
//
//            Button({ viewModel.addClick() }) {
//                Text("Click Me2")
//            }
        }
    }
}

@Composable
private fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .size(100.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}


