package com.happ.unitsconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.SwapHoriz
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Locale

@Composable
fun ConverterApp(
    title: String,
    units: Map<String, Double>,
    initialUnit: String,
    onDismiss: () -> Unit
) {
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
                var inputUnit by remember { mutableStateOf(initialUnit) }
                var outputUnit by remember { mutableStateOf(initialUnit) }
                var inputConversionFactor by remember { mutableDoubleStateOf(units[initialUnit] ?: 1.0) }
                var outputConversionFactor by remember { mutableDoubleStateOf(units[initialUnit] ?: 1.0) }
                var expandInput by remember { mutableStateOf(false) }
                var expandOutput by remember { mutableStateOf(false) }
                var netFactor by remember { mutableDoubleStateOf(1.0) }
                var result by remember { mutableDoubleStateOf(0.0) }

                fun calculateResult() {
                    val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
                    netFactor = inputConversionFactor / outputConversionFactor
                    result = inputValueDouble * netFactor
                }

                TitleText(title)
                InputField(title, inputValue, inputUnit) { newValue ->
                    inputValue = newValue
                    calculateResult()
                }

                UnitSelectionRow(
                    inputUnit,
                    outputUnit,
                    units,
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
                    expandInput,
                    expandOutput,
                    { expandInput = true },
                    { expandOutput = true }
                )

                Button(onClick = {
                    val tempUnit = inputUnit
                    inputUnit = outputUnit
                    outputUnit = tempUnit

                    val tempFactor = inputConversionFactor
                    inputConversionFactor = outputConversionFactor
                    outputConversionFactor = tempFactor
                    calculateResult()
                }) {
                    Icon(
                        imageVector = Icons.Filled.SwapHoriz,
                        contentDescription = "Reverse Units Buttons"
                    )
                }
                ResultText(result, outputUnit)
                HintText(netFactor)
            }
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = "$title Converter",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun InputField(title:String, inputValue: String, inputUnit: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = onValueChange,
        label = { Text("Enter $title in $inputUnit") },
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
    units: Map<String, Double>,
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
            onUnitChange = onInputUnitChange,
            units = units
        )
        Text(
            text = "to",
            modifier = Modifier.padding(12.dp)
        )
        UnitSelectionColumn(
            unit = outputUnit,
            expanded = expandOutput,
            onExpand = onExpandOutput,
            onUnitChange = onOutputUnitChange,
            units = units
        )
    }
}

@Composable
fun UnitSelectionColumn(
    unit: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    onUnitChange: (String, Double) -> Unit,
    units: Map<String, Double>
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
            onDismissRequest = {}
        ) {
            for ((unitName, factor) in units) {
                DropdownMenuItem(
                    text = { Text(unitName) },
                    onClick = { onUnitChange(unitName, factor) }
                )
            }
        }
    }
}

@Composable
fun ResultText(result: Double, outputUnit: String) {
    val formattedResult = formatResult(result)
    Text(
        text = "Result: $formattedResult $outputUnit",
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
fun HintText(netFactor: Double) {
    if (netFactor > 1.0) {
        val formattedFactor = formatResult(netFactor)
        Text(
            text = "Hint: Multiply by $formattedFactor",
        )
    } else if (netFactor < 1.0) {
        val formattedFactor = formatResult(1 / netFactor)
        Text(
            text = "Hint: Divide by $formattedFactor",
        )
    }
}

fun formatResult(value: Double): String {
    return if (value < 1.0 && value > 0.0) {
        String.format(Locale.getDefault(), "%.10f", value).trimEnd('0').trimEnd('.')
    } else {
        String.format(Locale.getDefault(), "%.2f", value)
    }
}
