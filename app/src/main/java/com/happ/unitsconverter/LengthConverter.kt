package com.happ.unitsconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Locale

@Composable
fun LengthConverterApp(onDismiss: () -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("m") }
    var outputUnit by remember { mutableStateOf("m") }
    var inputConversionFactor by remember { mutableDoubleStateOf(1.0) }
    var outputConversionFactor by remember { mutableDoubleStateOf(1.0) }
    var expandInput by remember { mutableStateOf(false) }
    var expandOutput by remember { mutableStateOf(false) }
    var netFactor by remember { mutableDoubleStateOf(1.0) }
    var result by remember { mutableDoubleStateOf(0.0) }


    fun calculateResult() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        netFactor = inputConversionFactor / outputConversionFactor
        result = (inputValueDouble * netFactor)
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Length Converter",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                        calculateResult()
                    },
                    label = { Text("Enter length in $inputUnit") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Center elements
                ) {
                    val units = mapOf(
                        "km" to 1000.0,
                        "m" to 1.0,
                        "cm" to 0.01,
                        "mm" to 0.001,
                        "µm" to 1e-6,
                        "nm" to 1e-9,
                        "ft" to 0.3048,
                        "in" to 0.0254,
                        "yd" to 0.9144,
                        "mi" to 1609.34,
                        "nmi" to 1852.0
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { expandInput = true }) {
                            Text(inputUnit)
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Input Dropdown Arrow"
                            )
                        }
                        DropdownMenu(
                            expanded = expandInput,
                            onDismissRequest = { expandInput = false }) {
                            for ((unit, factor) in units) {
                                DropdownMenuItem(
                                    text = { Text(unit) },
                                    onClick = {
                                        inputConversionFactor = factor
                                        calculateResult()
                                        inputUnit = unit
                                        expandInput = false
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        text = "to",
                        modifier = Modifier.padding(12.dp)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { expandOutput = true }) {
                            Text(outputUnit)
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Output Dropdown Arrow"
                            )
                        }
                        DropdownMenu(
                            expanded = expandOutput,
                            onDismissRequest = { expandOutput = false }) {
                            for ((unit, factor) in units) {
                                DropdownMenuItem(
                                    text = { Text(unit) },
                                    onClick = {
                                        outputConversionFactor = factor
                                        calculateResult()
                                        outputUnit = unit
                                        expandOutput = false
                                    }
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Result: $result $outputUnit",
                    modifier = Modifier.padding(top = 12.dp)
                )
                if (netFactor > 1.0) {
                    Text (
                        text= "Hint: Multiply by $netFactor",
                    )
                } else if (netFactor < 1.0) {
                    val roundedValue = String.format(Locale.getDefault(), "%.2f", 1/netFactor)
                    Text (
                        text= "Hint: Divide by $roundedValue",
                    )
                }
            }
        }
    }
}
