package com.happ.unitsconverter

import androidx.compose.runtime.Composable

val lengthUnits = mapOf(
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

val volumeUnits = mapOf(
    "L" to 1.0,
    "mL" to 0.001,
    "m³" to 1000.0,
    "cm³" to 0.000001,
    "mm³" to 0.000000001
)

val currencyUnits = mapOf(
    "INR" to 1.0,
    "USD" to 84.06,
    "EUR" to 89.90,
    "GBP" to 104.72,
    "JPY" to 0.54,
    "CHF" to 91.94,
    "CAD" to 61.02,
    "AUD" to 55.06,
    "SGD" to 61.67,
    "HKD" to 10.64,
    "CNY" to 11.66
)

val weightUnits = mapOf(
    "kg" to 1.0,
    "g" to 0.001,
    "mg" to 0.000001,
    "lb" to 0.453592,
    "oz" to 0.0283495,
    "tonne" to 1000.0
)


@Composable
fun LengthConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Length",
        units = lengthUnits,
        initialUnit = "m",
        onDismiss = onDismiss
    )
}

@Composable
fun VolumeConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Volume",
        units = volumeUnits,
        initialUnit = "L",
        onDismiss = onDismiss
    )
}

@Composable
fun CurrencyConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Currency",
        units = currencyUnits,
        initialUnit = "INR",
        onDismiss = onDismiss
    )
}

@Composable
fun WeightConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Weight",
        units = weightUnits,
        initialUnit = "kg",
        onDismiss = onDismiss
    )
}