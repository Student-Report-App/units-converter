package com.happ.unitsconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Locale

val units = mapOf(
    "L" to 1.0,
    "mL" to 0.001,
    "m³" to 1000.0,
    "cm³" to 0.000001,
    "mm³" to 0.000000001
)

@Composable
fun VolumeConverterApp(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var inputValue by remember { mutableStateOf("") }
                var inputUnit by remember { mutableStateOf("L") }
                var outputUnit by remember { mutableStateOf("L") }
                var inputConversionFactor by remember { mutableDoubleStateOf(1.0) }
                var outputConversionFactor by remember { mutableDoubleStateOf(1.0) }
                var expandInput by remember { mutableStateOf(false) }
                var expandOutput by remember { mutableStateOf(false) }
                var netFactor by remember { mutableDoubleStateOf(1.0) }
                var result by remember { mutableDoubleStateOf(0.0) }

                fun calculateResult() {
                    val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
                    netFactor = inputConversionFactor / outputConversionFactor
                    result = inputValueDouble * netFactor
                }

                TitleText()
                InputField(inputValue, inputUnit) { newValue ->
                    inputValue = newValue
                    calculateResult()
                }

                UnitSelectionRow(
                    inputUnit, outputUnit,
                    onInputUnitChange = { newUnit, factor ->
                        inputUnit = newUnit
                        inputConversionFactor = factor
                        calculateResult()
                        expandInput = false
                    },
                    onOutputUnitChange = { newUnit, factor ->
                        outputUnit = newUnit
                        outputConversionFactor = factor
                        calculateResult()
                        expandOutput = false
                    },
                    expandInput, expandOutput,
                    { expandInput = true },
                    { expandOutput = true }
                )

                ResultText(result, outputUnit)
                HintText(netFactor)
            }
        }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "Volume Converter",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun InputField(inputValue: String, inputUnit: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = onValueChange,
        label = { Text("Enter volume in $inputUnit") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}

@Composable
fun UnitSelectionRow(
    inputUnit: String,
    outputUnit: String,
    onInputUnitChange: (String, Double) -> Unit,
    onOutputUnitChange: (String, Double) -> Unit,
    expandInput: Boolean,
    expandOutput: Boolean,
    onExpandInput: () -> Unit,
    onExpandOutput: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UnitSelectionColumn(
            unit = inputUnit,
            expanded = expandInput,
            onExpand = onExpandInput,
            onUnitChange = onInputUnitChange
        )
        Text(
            text = "to",
            modifier = Modifier.padding(12.dp)
        )
        UnitSelectionColumn(
            unit = outputUnit,
            expanded = expandOutput,
            onExpand = onExpandOutput,
            onUnitChange = onOutputUnitChange
        )
    }
}

@Composable
fun UnitSelectionColumn(
    unit: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    onUnitChange: (String, Double) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onExpand) {
            Text(unit)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpand() }
        ) {
            for ((unit, factor) in units) {
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = { onUnitChange(unit, factor) }
                )
            }
        }
    }
}

@Composable
fun ResultText(result: Double, outputUnit: String) {
    Text(
        text = "Result: $result $outputUnit",
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
fun HintText(netFactor: Double) {
    if (netFactor > 1.0) {
        Text(
            text = "Hint: Multiply by $netFactor",
        )
    } else if (netFactor < 1.0) {
        val roundedValue = String.format(Locale.getDefault(), "%.2f", 1 / netFactor)
        Text(
            text = "Hint: Divide by $roundedValue",
        )
    }
}
