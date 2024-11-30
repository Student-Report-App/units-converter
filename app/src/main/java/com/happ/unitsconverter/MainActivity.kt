package com.happ.unitsconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.happ.unitsconverter.ui.theme.UnitsConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitsConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun HomeScreen(modifier: Modifier) {
    var showLength by remember { mutableStateOf(false) }
    var showVolume by remember { mutableStateOf(false) }
    var showNumber by remember { mutableStateOf(false) }

    if (showLength) {
        LengthConverterApp(onDismiss = { showLength = false })
    }
    if (showVolume) {
        VolumeConverterApp(onDismiss = { showVolume = false })
    }
    if (showNumber) {
        NumberConverterApp(onDismiss = { showNumber = false })
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "Units Converter",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        Row(
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = { showLength = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(50.dp),
            ) {
                Text(
                    text = "Length",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Button(
                onClick = { showVolume = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(50.dp),

            ) {
                Text(
                    text = "Volume",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Button(
                onClick = { showNumber = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(50.dp),
            ) {
                Text(
                    text = "Number",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}