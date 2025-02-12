package com.happ.unitsconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier) {
    var showLength by remember { mutableStateOf(false) }
    var showVolume by remember { mutableStateOf(false) }
    var showNumber by remember { mutableStateOf(false) }
    var showCurrency by remember { mutableStateOf(false) }
    var showWeight by remember { mutableStateOf(false) }

    if (showLength) LengthConverterApp(onDismiss = { showLength = false })
    if (showVolume) VolumeConverterApp(onDismiss = { showVolume = false })
    if (showNumber) NumberConverterApp(onDismiss = { showNumber = false })
    if (showCurrency) CurrencyConverterApp(onDismiss = { showCurrency = false })
    if (showWeight) WeightConverterApp(onDismiss = { showWeight = false })

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Units Converter",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp), // Increase font size
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(16.dp)
        )

        Row(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            ConverterButton(
                text = "Length",
                onClick = { showLength = true },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            ConverterButton(
                text = "Volume",
                onClick = { showVolume = true },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            ConverterButton(
                text = "Number",
                onClick = { showNumber = true },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            ConverterButton(
                text = "Currency",
                onClick = { showCurrency = true },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            ConverterButton(
                text = "Weight",
                onClick = { showWeight = true },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ConverterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp)
    ) {
        Text(text = text)
    }
}