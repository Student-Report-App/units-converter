package com.happ.unitsconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun NumberConverterApp(onDismiss: () -> Unit) {
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
                var inputValue by remember { mutableStateOf("") }
                var inputSystem by remember { mutableStateOf("Decimal") }
                var outputSystem by remember { mutableStateOf("Decimal") }
                var expandInput by remember { mutableStateOf(false) }
                var expandOutput by remember { mutableStateOf(false) }
                var result by remember { mutableStateOf("") }

                fun convertToDecimal(inputValue: String, base: Int, validChars: Set<Char>): Int {
                    return try {
                        if (inputValue.all { it in validChars }) {
                            Integer.parseInt(inputValue, base)
                        } else {
                            0
                        }
                    } catch (_: NumberFormatException) {
                        0
                    }
                }

                fun binaryToDecimal(binaryString: String): Int {
                    val validBinaryChars = setOf('0', '1')
                    return convertToDecimal(binaryString, 2, validBinaryChars)
                }

                fun octalToDecimal(octalString: String): Int {
                    val validOctalChars = ('0'..'7').toSet()
                    return convertToDecimal(octalString, 8, validOctalChars)
                }

                fun hexToDecimal(hexString: String): Int {
                    val validHexChars = ('0'..'9').toSet() + ('A'..'F').toSet() + ('a'..'f').toSet()
                    return convertToDecimal(hexString, 16, validHexChars)
                }

                fun calculateResult() {
                    if (!inputValue.isEmpty()) {
                        val inputInDecimal = when (inputSystem) {
                            "Decimal" -> inputValue.toIntOrNull() ?: 0
                            "Binary" -> binaryToDecimal(inputValue)
                            "Octal" -> octalToDecimal(inputValue)
                            "Hex" -> hexToDecimal(inputValue)
                            else -> 0
                        }
                        val decimalToOutput = when (outputSystem) {
                            "Decimal" -> inputInDecimal
                            "Binary" -> Integer.toBinaryString(inputInDecimal.toInt())
                            "Octal" -> Integer.toOctalString(inputInDecimal.toInt())
                            "Hex" -> Integer.toHexString(inputInDecimal.toInt())
                            else -> 0
                        }
                        result = decimalToOutput.toString().uppercase()
                    }
                }

                Text(
                    text = "Number Converter",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                        calculateResult()
                    },
                    label = { Text("Enter a value") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val numberSystems = listOf<String>("Decimal", "Binary", "Octal", "Hex")

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { expandInput = true }) {
                            Text(inputSystem)
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Input Dropdown Arrow"
                            )
                        }
                        DropdownMenu(
                            expanded = expandInput,
                            onDismissRequest = { expandInput = false }) {
                            for (system in numberSystems) {
                                DropdownMenuItem(
                                    text = { Text(system) },
                                    onClick = {
                                        inputSystem = system
                                        calculateResult()
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
                            Text(outputSystem)
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Output Dropdown Arrow"
                            )
                        }
                        DropdownMenu(
                            expanded = expandOutput,
                            onDismissRequest = { expandOutput = false }) {
                            for (system in numberSystems) {
                                DropdownMenuItem(
                                    text = { Text(system) },
                                    onClick = {
                                        outputSystem = system
                                        calculateResult()
                                        expandOutput = false
                                    }
                                )
                            }
                        }
                    }
                }
                Text(
                    text = "Result: $result",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
